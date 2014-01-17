package org.vertexarmy.omniray.raytracer.geometry;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.vertexarmy.omniray.jglm.Vec3;
import org.vertexarmy.omniray.raytracer.HitResult;
import org.vertexarmy.omniray.raytracer.Ray;

/**
 * User: Alex
 * Date: 10/27/13
 * Time: 1:45 PM
 */
public class TestRaySphereIntersection {
    private Ray ray;
    private SphereHitResolver sphereHitResolver;

    @BeforeTest
    public void SetUp() {
        ray = new Ray(Vec3.ZERO, Vec3.UNIT_Z);
        sphereHitResolver = new SphereHitResolver();
    }

    @Test
    public void testSphereAheadOfRay() {
        sphereHitResolver.setSphere(GeometryToolkit.buildSphere(new Vec3(0, 0, 10), 2));
        HitResult hitResult = sphereHitResolver.resolve(ray);
        Assert.assertTrue(hitResult.isHit());
        Assert.assertEquals(hitResult.getRayHitLocation(), 8.0f);
        Assert.assertEquals(hitResult.getLocalHitLocation(), new Vec3(0, 0, 8));
    }

    @Test
    public void testSphereBehindRay() {
        sphereHitResolver.setSphere(GeometryToolkit.buildSphere(new Vec3(0, 0, -10), 2));
        HitResult hitResult = sphereHitResolver.resolve(ray);
        Assert.assertFalse(hitResult.isHit());
    }

    @Test
    public void testRayInsideSphere() {
        sphereHitResolver.setSphere(GeometryToolkit.buildSphere(new Vec3(0, 0, -1), 3));
        HitResult hitResult = sphereHitResolver.resolve(ray);
        Assert.assertTrue(hitResult.isHit());
        Assert.assertEquals(hitResult.getRayHitLocation(), 2.0f);
        Assert.assertEquals(hitResult.getLocalHitLocation(), new Vec3(0, 0, 2));
    }

    @Test
    public void testNoIntersection() {
        sphereHitResolver.setSphere(GeometryToolkit.buildSphere(new Vec3(0, 5, 0), 4));
        HitResult hitResult = sphereHitResolver.resolve(ray);
        Assert.assertFalse(hitResult.isHit());
    }

}
