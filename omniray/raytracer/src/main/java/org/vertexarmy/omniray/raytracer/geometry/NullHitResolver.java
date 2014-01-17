package org.vertexarmy.omniray.raytracer.geometry;

import org.vertexarmy.omniray.raytracer.HitResult;
import org.vertexarmy.omniray.raytracer.Ray;

/**
 * User: Alex
 * Date: 1/17/14
 */
public class NullHitResolver implements HitResolver {
    @Override
    public HitResult resolve(Ray ray) {
        return HitResult.NO_HIT;
    }
}
