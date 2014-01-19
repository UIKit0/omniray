package org.vertexarmy.omniray.raytracer.geometry;

import org.vertexarmy.omniray.raytracer.Datastructures;

/**
 * User: Alex
 * Date: 1/17/14
 */

/**
 * Factory used to create HitResolvers.
 * <p/>
 * This factory caches the resolvers and returns the appropiate one
 * depending on the type of the GeometricObject
 */
public class HitResolverFactory {
    private final PlaneHitResolver planeHitResolver = new PlaneHitResolver();
    private final SphereHitResolver sphereHitResolver = new SphereHitResolver();
    private final NullHitResolver nullHitResolver = new NullHitResolver();

    /**
     * Returns a hit resolver that knows how to trace this geometric object
     *
     * @param object a geometric object containing a surface model
     * @return a resolver that can trace rays for the surface model contained inside the object
     */
    public HitResolver getResolver(Datastructures.GeometricObject object) {
        if (object.hasPlane()) {
            planeHitResolver.setPlane(object.getPlane());
            return planeHitResolver;
        }

        if (object.hasSphere()) {
            sphereHitResolver.setSphere(object.getSphere());
            return sphereHitResolver;
        }
        return nullHitResolver;
    }
}
