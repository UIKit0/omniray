package net.vertexarmy.omniray.raytracer.geometry;

import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.support.FastMath;
import lombok.Getter;
import net.vertexarmy.omniray.raytracer.HitResult;
import net.vertexarmy.omniray.raytracer.Ray;

/**
 * User: Alex
 * Date: 10/27/13
 * Time: 1:19 PM
 */
public class Sphere implements GeometricObject {
    @Getter
    private final Vec3 origin;
    private final float radius;

    public Sphere(Vec3 origin, float radius) {
        this.origin = origin;
        this.radius = radius;
    }

    @Override
    public HitResult hit(Ray ray) {
        float t;
        Vec3 temp = ray.getOrigin().subtract(origin);
        float a = ray.getDirection().dot(ray.getDirection());
        float b = temp.multiply(2.0f).dot(ray.getDirection());
        float c = temp.dot(temp) - radius * radius;
        double disc = b * b - 4.0 * a * c;

        if (disc < 0.0) {
            return HitResult.NO_HIT;
        } else {
            float e = (float) FastMath.sqrt(disc);
            float denom = 2.0f * a;
            t = (-b - e) / denom;
            if (t <= EPSILON) {
                t = (-b + e) / denom;
            }
            if (t > EPSILON) {
                return HitResult.builder()
                        .hit(true)
                        .rayHitLocation(t)
                        .localHitLocation(ray.getOrigin().add(ray.getDirection().multiply(t)))
                        .normal(temp.add(ray.getDirection().multiply(t)).scale(1.0f / radius))
                        .build();
            }
        }
        return HitResult.NO_HIT;
    }
}
