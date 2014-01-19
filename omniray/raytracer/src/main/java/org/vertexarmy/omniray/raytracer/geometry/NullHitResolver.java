package org.vertexarmy.omniray.raytracer.geometry;

import org.vertexarmy.omniray.raytracer.HitResult;
import org.vertexarmy.omniray.raytracer.Ray;

/**
 * User: Alex
 * Date: 1/17/14
 */

/**
 * A null hit resolver that never returns a hit.
 */
public class NullHitResolver implements HitResolver {

    /**
     * Always returns NO_HIT.
     *
     * @param ray the ray to trace
     * @return HitResult.NO_HIT
     */
    @Override
    public HitResult resolve(Ray ray) {
        return HitResult.NO_HIT;
    }
}
