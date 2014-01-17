package org.vertexarmy.omniray.raytracer.geometry;

import lombok.Setter;
import org.vertexarmy.omniray.jglm.Vec3;
import org.vertexarmy.omniray.raytracer.Datastructures;
import org.vertexarmy.omniray.raytracer.HitResult;
import org.vertexarmy.omniray.raytracer.Ray;

/**
 * User: Alex
 * Date: 10/27/13
 * Time: 12:31 PM
 */
public class PlaneHitResolver implements HitResolver {
    @Setter
    private Datastructures.GeometricObject.Plane plane;

    @Override
    public HitResult resolve(final Ray ray) {
        Vec3 origin = new Vec3(plane.getOrigin().getX(), plane.getOrigin().getY(), plane.getOrigin().getZ());
        Vec3 normal = new Vec3(plane.getNormal().getX(), plane.getNormal().getY(), plane.getNormal().getZ());

        float rayHitLocation = origin.subtract(ray.getOrigin()).dot(normal) / (ray.getDirection().dot(normal));
        if (rayHitLocation > EPSILON && rayHitLocation != Double.POSITIVE_INFINITY) {
            return HitResult.builder()
                    .hit(true)
                    .rayHitLocation(rayHitLocation)
                    .localHitLocation(ray.getOrigin().add(ray.getDirection().multiply(rayHitLocation)))
                    .normal(normal)
                    .build();
        }
        return HitResult.NO_HIT;
    }
}