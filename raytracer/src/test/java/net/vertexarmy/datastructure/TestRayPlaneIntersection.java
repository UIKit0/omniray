package org.vertexarmy.datastructure;

import com.hackoeur.jglm.Vec3;
import net.vertexarmy.omniray.raytracer.HitResult;
import net.vertexarmy.omniray.raytracer.Ray;
import net.vertexarmy.omniray.raytracer.geometry.Plane;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * User: Alex
 * Date: 10/27/13
 * Time: 1:31 PM
 */
public class TestRayPlaneIntersection {
    @Test
    public void testPerpendicularIntersection() {
        Ray ray = new Ray(Vec3.ZERO, new Vec3(0, 0, 1));

        float planeLocationZ[] = new float[]{0.1f, 1.0f, 0.32f};

        for (float locationZ : planeLocationZ) {
            Plane plane = new Plane(new Vec3(0, 0, locationZ), Vec3.UNIT_Z);
            HitResult hitResult = plane.hit(ray);
            Assert.assertTrue(hitResult.isHit(), String.format("Plane at depth %f was not hit by ray.", locationZ));
            Assert.assertEquals(hitResult.getLocalHitLocation().getZ(), locationZ);
        }
    }

    @Test
    public void testPerpendicularNoIntersection() {
        Ray ray = new Ray(Vec3.ZERO, new Vec3(0, 0, 1));

        float planeLocationZ[] = new float[]{0.0f, -32.0f, -0.1f};

        for (float locationZ : planeLocationZ) {
            Plane plane = new Plane(new Vec3(0, 0, locationZ), Vec3.UNIT_Z);
            HitResult hitResult = plane.hit(ray);
            Assert.assertFalse(hitResult.isHit(), String.format("Plane at depth %f was hit by ray.", locationZ));
        }
    }

    @Test
    public void testParallelRayIntersection() {
        Ray ray = new Ray(Vec3.ZERO, new Vec3(0, 0, 1));
        Plane plane = new Plane(new Vec3(0, 0, -1), Vec3.UNIT_Y);
        HitResult hitResult = plane.hit(ray);

        Assert.assertFalse(hitResult.isHit());
    }
}
