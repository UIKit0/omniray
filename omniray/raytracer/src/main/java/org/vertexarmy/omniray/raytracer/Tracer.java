package org.vertexarmy.omniray.raytracer;

import org.vertexarmy.omniray.jglm.Vec3;
import org.vertexarmy.omniray.raytracer.geometry.HitResolverFactory;
import org.vertexarmy.omniray.raytracer.material.ColorToolkit;
import org.vertexarmy.omniray.raytracer.material.texture.RadialGradientTexture;
import org.vertexarmy.omniray.raytracer.material.texture.TextureSampler;
import org.vertexarmy.omniray.raytracer.sampling.RandomSampler;
import org.vertexarmy.omniray.raytracer.sampling.Sampler;

/**
 * User: Alex
 * Date: 1/17/14
 */
public class Tracer {

    private final HitResolverFactory hitResolverFactory;

    public Tracer() {
        hitResolverFactory = new HitResolverFactory();
    }

    public void render(Datastructures.Task task, ImageBuilder builder) {

        Datastructures.ViewPlane viewPlane = task.getViewPlane();
        builder.begin();

        final RadialGradientTexture backgroundTexture = new RadialGradientTexture(ColorToolkit.fromRGB(80, 80, 80), ColorToolkit.fromRGB(40, 40, 40));
        final TextureSampler backgroundSampler = new TextureSampler(backgroundTexture, TextureSampler.FilterType.NEAREST);

        Sampler antialiasSampler = new RandomSampler(16);


        Ray ray = new Ray(new Vec3(0, 0, 0), new Vec3(0, 0, 1));
        for (int r = viewPlane.getX(); r < viewPlane.getWidth(); ++r) {
            for (int c = viewPlane.getY(); c < viewPlane.getHeight(); ++c) {

                int redAccumulator = 0;
                int greenAccumulator = 0;
                int blueAccumulator = 0;

                for (int sample = 0; sample < antialiasSampler.sampleCount(); ++sample) {

                    Vec3 sampleOffset = antialiasSampler.nextSample();
                    float sampleX = sampleOffset.getX() - 0.5f;
                    float sampleY = sampleOffset.getY() - 0.5f;

                    // create the ray
                    ray.setOrigin(new Vec3(r + sampleX, c + sampleY, 0));
                    ray.setDirection(Vec3.UNIT_Z);

                    // trace the ray
                    HitResult hitResult = traceRay(task.getWorld(), ray);

                    float u = (float) (r - viewPlane.getX()) / (viewPlane.getWidth() - viewPlane.getX());
                    float v = (float) (c - viewPlane.getY()) / (viewPlane.getHeight() - viewPlane.getY());

                    // calculate the color
                    int sampleColor = hitResult == HitResult.NO_HIT ?
                            backgroundSampler.getColor(u, v) :
                            hitResult.getColor();

                    redAccumulator += ColorToolkit.getRed(sampleColor);
                    greenAccumulator += ColorToolkit.getGreen(sampleColor);
                    blueAccumulator += ColorToolkit.getBlue(sampleColor);
                }

                int color = ColorToolkit.fromRGB(
                        redAccumulator / antialiasSampler.sampleCount(),
                        greenAccumulator / antialiasSampler.sampleCount(),
                        blueAccumulator / antialiasSampler.sampleCount()
                );

                // render the result
                builder.setColor(r - viewPlane.getX(), c - viewPlane.getY(), color);
            }
        }

        builder.end();
    }

    private HitResult traceRay(Datastructures.World world, Ray ray) {
        HitResult closestHitResult = HitResult.NO_HIT;
        for (Datastructures.GeometricObject object : world.getGeometricObjectList()) {
            HitResult result = hitResolverFactory.getResolver(object).resolve(ray);
            if (result.isHit() && (closestHitResult == HitResult.NO_HIT || result.getRayHitLocation() < closestHitResult.getRayHitLocation())) {
                closestHitResult = result;
            }
        }
        return closestHitResult;
    }
}
