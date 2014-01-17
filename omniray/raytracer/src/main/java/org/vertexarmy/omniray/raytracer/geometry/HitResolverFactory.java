package org.vertexarmy.omniray.raytracer.geometry;

import org.vertexarmy.omniray.raytracer.Datastructures;

/**
 * User: Alex
 * Date: 1/17/14
 */
public class HitResolverFactory {
    private final PlaneHitResolver planeHitResolver = new PlaneHitResolver();
    private final SphereHitResolver sphereHitResolver = new SphereHitResolver();
    private final NullHitResolver nullHitResolver = new NullHitResolver();

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
