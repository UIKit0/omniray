package net.vertexarmy.omniray.raytracer;

import com.hackoeur.jglm.Vec3;
import lombok.Getter;

/**
 * User: Alex
 * Date: 10/27/13
 * Time: 11:34 AM
 */
public class Ray {
    @Getter
    private final Vec3 direction;
    @Getter
    private final Vec3 origin;

    public Ray(Vec3 origin, Vec3 direction) {
        this.direction = direction;
        this.origin = origin;
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && other instanceof Ray) {
            Ray otherRay = (Ray) other;
            return otherRay.getDirection().equals(direction) && otherRay.getOrigin().equals(origin);
        }
        return false;
    }
}
