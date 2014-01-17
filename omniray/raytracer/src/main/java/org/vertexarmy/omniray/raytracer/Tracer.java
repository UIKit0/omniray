package org.vertexarmy.omniray.raytracer;

import org.vertexarmy.omniray.jglm.Vec3;
import org.vertexarmy.omniray.raytracer.geometry.HitResolverFactory;

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

        Ray ray = new Ray(new Vec3(0, 0, 0), new Vec3(0, 0, 1));
        for (int r = -viewPlane.getWidth() / 2; r < viewPlane.getWidth() / 2; ++r) {
            for (int c = -viewPlane.getHeight() / 2; c < viewPlane.getHeight() / 2; ++c) {

                // trace samples
                // integrate samples
                // send the color to the builder

                // create the ray
                ray.setOrigin(new Vec3(r, c, 0));
                ray.setDirection(Vec3.UNIT_Z);

                // trace the ray
                HitResult hitResult = traceRay(task.getWorld(), ray);

                // calculate the color
                int color = hitResult == null ? 0x323232 : hitResult.getColor();

                // render the result
                builder.setColor(r + viewPlane.getWidth() / 2, c + viewPlane.getHeight() / 2, color);
            }
        }

        builder.end();
    }

    private HitResult traceRay(Datastructures.World world, Ray ray) {
        HitResult closestHitResult = null;
        for (Datastructures.GeometricObject object : world.getGeometricObjectList()) {
            HitResult result = hitResolverFactory.getResolver(object).resolve(ray);
            if (result.isHit() && (closestHitResult == null || result.getRayHitLocation() < closestHitResult.getRayHitLocation())) {
                closestHitResult = result;
            }
        }
        return closestHitResult;
    }
}
