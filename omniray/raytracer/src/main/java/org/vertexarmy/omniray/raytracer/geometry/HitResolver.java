package org.vertexarmy.omniray.raytracer.geometry;

import org.vertexarmy.omniray.raytracer.HitResult;
import org.vertexarmy.omniray.raytracer.Ray;

/**
 * User: Alex
 * Date: 10/27/13
 * Time: 12:11 PM
 */

/**
 * A hit resolver's responsibility is to compute the intersection
 * between a geometric object and a ray.
 * <p/>
 * By using hit resolvers, we separate the form of the geometric object
 * from the algorithm used to compute the intersection. This allows us to
 * represent the mathematical model of the object in a more serialization-friendly way.
 */
public interface HitResolver {
    static final double EPSILON = 0.001;

    /**
     * Resolves the intersection for this ray.
     *
     * @param ray the ray to trace
     * @return the result of the intersection
     */
    HitResult resolve(final Ray ray);
}
