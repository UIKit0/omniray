package org.vertexarmy.omniray.raytracer.geometry;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.vertexarmy.omniray.jglm.Vec3;
import org.vertexarmy.omniray.raytracer.Datastructures;

/**
 * User: Alex
 * Date: 1/19/14
 */
public class TestGeometryToolkit {

    @Test
    public void testGeometricObjectConstruction() {
        Datastructures.GeometricObject geometricObject;
        Datastructures.GeometricObject.Plane plane = GeometryToolkit.buildPlane(new Vec3(0, 0, 0), new Vec3(0, 1, 0));
        geometricObject = GeometryToolkit.toGeometricObject(plane);

        Assert.assertTrue(geometricObject.hasPlane());
        Assert.assertFalse(geometricObject.hasSphere());
        Assert.assertEquals(plane, geometricObject.getPlane());

        Datastructures.GeometricObject.Sphere sphere = GeometryToolkit.buildSphere(new Vec3(0, 0, 0), 30);
        geometricObject = GeometryToolkit.toGeometricObject(sphere);

        Assert.assertTrue(geometricObject.hasSphere());
        Assert.assertFalse(geometricObject.hasPlane());
        Assert.assertEquals(sphere, geometricObject.getSphere());
    }
}
