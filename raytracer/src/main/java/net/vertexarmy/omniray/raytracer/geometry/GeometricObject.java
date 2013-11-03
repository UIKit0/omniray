package net.vertexarmy.omniray.raytracer.geometry;

import net.vertexarmy.omniray.raytracer.HitResult;
import net.vertexarmy.omniray.raytracer.Ray;

/**
 * User: Alex
 * Date: 10/27/13
 * Time: 12:11 PM
 */
public interface GeometricObject {
    static final double EPSILON = 0.001;

    HitResult hit(final Ray ray);
}
