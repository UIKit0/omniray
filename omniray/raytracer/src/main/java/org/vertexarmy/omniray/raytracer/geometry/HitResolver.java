package org.vertexarmy.omniray.raytracer.geometry;

import org.vertexarmy.omniray.raytracer.HitResult;
import org.vertexarmy.omniray.raytracer.Ray;

/**
 * User: Alex
 * Date: 10/27/13
 * Time: 12:11 PM
 */
public interface HitResolver {
    static final double EPSILON = 0.001;

    HitResult resolve(final Ray ray);
}
