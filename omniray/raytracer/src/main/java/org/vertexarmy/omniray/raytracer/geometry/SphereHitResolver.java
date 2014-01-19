package org.vertexarmy.omniray.raytracer.geometry;

import lombok.Setter;
import org.vertexarmy.omniray.jglm.Vec3;
import org.vertexarmy.omniray.jglm.support.FastMath;
import org.vertexarmy.omniray.raytracer.Datastructures;
import org.vertexarmy.omniray.raytracer.HitResult;
import org.vertexarmy.omniray.raytracer.Ray;
import org.vertexarmy.omniray.raytracer.material.ColorToolkit;

/**
 * User: Alex
 * Date: 10/27/13
 * Time: 1:19 PM
 */

/**
 * A hit resolver that can intersect rays and spheres.
 */
public class SphereHitResolver implements HitResolver {
    @Setter
    private Datastructures.GeometricObject.Sphere sphere;

    /**
     * Intersects the ray with the wrapped sphere
     *
     * @param ray the ray to trace
     * @return a hit result on the surface of the sphere
     */
    @Override
    public HitResult resolve(Ray ray) {
        final Vec3 origin = new Vec3(sphere.getCenter().getX(), sphere.getCenter().getY(), sphere.getCenter().getZ());
        final float radius = sphere.getRadius();

        Vec3 temp = ray.getOrigin().subtract(origin);
        float a = ray.getDirection().dot(ray.getDirection());
        float b = temp.multiply(2.0f).dot(ray.getDirection());
        float c = temp.dot(temp) - radius * radius;
        double disc = b * b - 4.0 * a * c;

        if (disc < 0.0) {
            return HitResult.NO_HIT;
        } else {
            float e = (float) Math.sqrt(disc);
            float denom = 2.0f * a;
            float t = (-b - e) / denom;
            if (t <= EPSILON) {
                t = (-b + e) / denom;
            }
            if (t > EPSILON) {
                return HitResult.builder()
                        .hit(true)
                        .rayHitLocation(t)
                        .localHitLocation(ray.getOrigin().add(ray.getDirection().multiply(t)))
                        .normal(temp.add(ray.getDirection().multiply(t)).scale(1.0f / radius))
                        .color(ColorToolkit.fromRGB((int) FastMath.min(255, FastMath.max(0, 255 - t)), 0, 0))
                        .build();
            }
        }
        return HitResult.NO_HIT;
    }
}
