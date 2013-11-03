package net.vertexarmy.omniray.raytracer.geometry;

import com.hackoeur.jglm.Vec3;
import lombok.Getter;
import net.vertexarmy.omniray.raytracer.HitResult;
import net.vertexarmy.omniray.raytracer.Ray;

/**
 * User: Alex
 * Date: 10/27/13
 * Time: 12:31 PM
 */
public class Plane implements GeometricObject {
    @Getter
    private final Vec3 origin;
    @Getter
    private final Vec3 normal;

    public Plane(Vec3 origin, Vec3 normal) {
        this.origin = origin;
        this.normal = normal;
    }

    @Override
    public HitResult hit(final Ray ray) {
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
