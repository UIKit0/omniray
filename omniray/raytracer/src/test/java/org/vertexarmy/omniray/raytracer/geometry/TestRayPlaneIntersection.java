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
 * Time: 1:31 PM
 */
public class TestRayPlaneIntersection {

    private PlaneHitResolver resolver;

    @BeforeTest
    public void setUp() {
        resolver = new PlaneHitResolver();
    }

    @Test
    public void testPerpendicularIntersection() {
        Ray ray = new Ray(Vec3.ZERO, new Vec3(0, 0, 1));

        ray.setOrigin(Vec3.ZERO);
        ray.setDirection(new Vec3(0, 0, 1));

        float planeLocationZ[] = new float[]{0.1f, 1.0f, 0.32f};

        for (float locationZ : planeLocationZ) {
            resolver.setPlane(GeometryToolkit.buildPlane(new Vec3(0, 0, locationZ), Vec3.UNIT_Z));
            HitResult hitResult = resolver.resolve(ray);
            Assert.assertTrue(hitResult.isHit(), String.format("PlaneHitResolver at depth %f was not resolve by ray.", locationZ));
            Assert.assertEquals(hitResult.getLocalHitLocation().getZ(), locationZ);
        }
    }

    @Test
    public void testPerpendicularNoIntersection() {
        Ray ray = new Ray(Vec3.ZERO, new Vec3(0, 0, 1));
        float planeLocationZ[] = new float[]{0.0f, -32.0f, -0.1f};
        for (float locationZ : planeLocationZ) {
            resolver.setPlane(GeometryToolkit.buildPlane(new Vec3(0, 0, locationZ), Vec3.UNIT_Z));
            HitResult hitResult = resolver.resolve(ray);
            Assert.assertFalse(hitResult.isHit(), String.format("PlaneHitResolver at depth %f was resolve by ray.", locationZ));
        }
    }

    @Test
    public void testParallelRayIntersection() {
        Ray ray = new Ray(Vec3.ZERO, new Vec3(0, 0, 1));
        resolver.setPlane(GeometryToolkit.buildPlane(new Vec3(0, 0, -1), Vec3.UNIT_Y));
        HitResult hitResult = resolver.resolve(ray);
        Assert.assertFalse(hitResult.isHit());
    }
}
