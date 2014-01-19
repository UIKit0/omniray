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

/**
 * The tracer class generates an image by tracing rays through a world.
 */
public class Tracer {

    private final HitResolverFactory hitResolverFactory;

    /**
     * Constructs a default tracer.
     */
    public Tracer() {
        hitResolverFactory = new HitResolverFactory();
    }

    /**
     * Builds an image by tracing this task and using the ImageBuilder to generate the result
     *
     * @param task    a ray tracing task
     * @param builder an image builder that will be used to construct the image
     */
    public void render(Datastructures.Task task, ImageBuilder builder) {

        Datastructures.ViewPlane viewPlane = task.getViewPlane();
        builder.begin();

        final RadialGradientTexture backgroundTexture = new RadialGradientTexture(ColorToolkit.fromRGB(80, 80, 80), ColorToolkit.fromRGB(40, 40, 40));
        final TextureSampler backgroundSampler = new TextureSampler(backgroundTexture, TextureSampler.FilterType.NEAREST);

        Sampler antialiasSampler = new RandomSampler(16);

        Datastructures.Rect renderRect = task.getRenderSection();
        Ray ray = new Ray(new Vec3(0, 0, 0), new Vec3(0, 0, 1));
        for (int r = renderRect.getX(); r < renderRect.getWidth(); ++r) {
            for (int c = renderRect.getY(); c < renderRect.getHeight(); ++c) {

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

                    float u = (float) (r - renderRect.getX()) / (renderRect.getWidth() - renderRect.getX());
                    float v = (float) (c - renderRect.getY()) / (renderRect.getHeight() - renderRect.getY());

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
                builder.setColor(r - renderRect.getX(), c - renderRect.getY(), color);
            }
        }

        builder.end();
    }

    /**
     * Traces a ray through the world by intersecting the ray with all the objects in the world
     *
     * @param world the world
     * @param ray   the ray
     * @return the hit result
     */
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
