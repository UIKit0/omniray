package org.vertexarmy.omniray.raytracer.geometry;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.vertexarmy.omniray.jglm.Vec3;
import org.vertexarmy.omniray.raytracer.Datastructures;
import org.vertexarmy.omniray.raytracer.HitResult;
import org.vertexarmy.omniray.raytracer.Ray;

/**
 * User: Alex
 * Date: 1/19/14
 */
public class TestHitResolvers {

    @Test
    public void testHitResolverFactory() {
        HitResolverFactory factory = new HitResolverFactory();
        Assert.assertTrue(factory.getResolver(GeometryToolkit.toGeometricObject(GeometryToolkit.buildPlane(Vec3.ZERO, Vec3.UNIT_X))) instanceof PlaneHitResolver);
        Assert.assertTrue(factory.getResolver(GeometryToolkit.toGeometricObject(GeometryToolkit.buildSphere(Vec3.ZERO, 0))) instanceof SphereHitResolver);
        Assert.assertTrue(factory.getResolver(Datastructures.GeometricObject.getDefaultInstance()) instanceof NullHitResolver);
    }

    @Test
    public void testNullHitResolver() {
        NullHitResolver hitResolver = new NullHitResolver();
        Assert.assertEquals(HitResult.NO_HIT, hitResolver.resolve(new Ray(Vec3.ZERO, Vec3.ZERO)));
    }
}
