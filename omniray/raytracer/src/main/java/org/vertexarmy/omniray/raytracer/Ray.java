package org.vertexarmy.omniray.raytracer;

import lombok.Getter;
import lombok.Setter;
import org.vertexarmy.omniray.jglm.Vec3;

/**
 * User: Alex
 * Date: 10/27/13
 * Time: 11:34 AM
 */

/**
 * Class used to represent a Ray.
 * A ray consists of an origin point, and a direction. The
 * reason instances of this class are not immutable is time
 * and memory consumption optimization.
 */
public class Ray {

    /**
     * The direction of the ray.
     */
    @Getter
    @Setter
    private Vec3 direction;

    /**
     * The origin of the ray.
     */
    @Getter
    @Setter
    private Vec3 origin;

    /**
     * Constructs a ray from an origin and a direction.
     *
     * @param origin    the origin of the ray
     * @param direction the direction of the ray
     */
    public Ray(Vec3 origin, Vec3 direction) {
        this.direction = direction;
        this.origin = origin;
    }

    /**
     * Generates a hash code for the ray. Defaults to super.
     *
     * @return a unique hash code for this ray.
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Compares this ray with another. The comparison results in a false result if
     * the second object is not a ray, or their origins and directions don't match.
     *
     * @param other the compared object
     * @return true if the two objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other != null && other instanceof Ray) {
            Ray otherRay = (Ray) other;
            return otherRay.getDirection().equals(direction) && otherRay.getOrigin().equals(origin);
        }
        return false;
    }
}
