package net.vertexarmy.datastructure;

import com.hackoeur.jglm.Vec3;
import junit.framework.Assert;
import net.vertexarmy.omniray.raytracer.HitResult;
import net.vertexarmy.omniray.raytracer.Ray;
import net.vertexarmy.omniray.raytracer.geometry.Sphere;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * User: Alex
 * Date: 10/27/13
 * Time: 1:45 PM
 */
public class TestRaySphereIntersection {
    private Ray ray;

    @BeforeTest
    public void SetUp(){
        ray = new Ray(Vec3.ZERO, Vec3.UNIT_Z);
    }

    @Test
    public void testSphereAheadOfRay() {
        Sphere sphere = new Sphere(new Vec3(0, 0, 10), 2);
        HitResult hitResult = sphere.hit(ray);
        Assert.assertTrue(hitResult.isHit());
        Assert.assertEquals(hitResult.getRayHitLocation(), 8.0f);
        Assert.assertEquals(hitResult.getLocalHitLocation(), new Vec3(0, 0, 8));
    }

    @Test
    public void testSphereBehindRay() {
        Sphere sphere = new Sphere(new Vec3(0, 0, -10), 2);
        HitResult hitResult = sphere.hit(ray);
        Assert.assertFalse(hitResult.isHit());
    }

    @Test
    public void testRayInsideSphere() {
        Sphere sphere = new Sphere(new Vec3(0, 0, -1), 3);
        HitResult hitResult = sphere.hit(ray);
        Assert.assertTrue(hitResult.isHit());
        Assert.assertEquals(hitResult.getRayHitLocation(), 2.0f);
        Assert.assertEquals(hitResult.getLocalHitLocation(), new Vec3(0, 0, 2));
    }

    @Test
    public void testNoIntersection() {
        Sphere sphere = new Sphere(new Vec3(0, 5, 0), 4);
        HitResult hitResult = sphere.hit(ray);
        Assert.assertFalse(hitResult.isHit());
    }

}
