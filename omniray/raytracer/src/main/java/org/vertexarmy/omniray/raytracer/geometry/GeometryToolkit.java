package org.vertexarmy.omniray.raytracer.geometry;

import org.vertexarmy.omniray.jglm.Vec3;
import org.vertexarmy.omniray.raytracer.Datastructures;

/**
 * User: Alex
 * Date: 1/17/14
 */

/**
 * Toolkit class used for constructing and operating on geometry objects.
 */
public class GeometryToolkit {

    /**
     * Builds a Plane data structure.
     *
     * @param origin the origin of the plane
     * @param normal the surface normal of the plane
     * @return a plane data structure
     */
    public static Datastructures.GeometricObject.Plane buildPlane(final Vec3 origin, final Vec3 normal) {
        return Datastructures.GeometricObject.Plane.newBuilder()
                .setOrigin(Datastructures.Vec3.newBuilder()
                        .setX(origin.getX())
                        .setY(origin.getY())
                        .setZ(origin.getZ())
                        .build())
                .setNormal(Datastructures.Vec3.newBuilder()
                        .setX(normal.getX())
                        .setY(normal.getY())
                        .setZ(normal.getZ())
                        .build())
                .build();
    }

    /**
     * Builds a Sphere data structure.
     *
     * @param center the origin of the sphere
     * @param radius the radius of the sphere
     * @return a sphere data structure
     */
    public static Datastructures.GeometricObject.Sphere buildSphere(final Vec3 center, final float radius) {
        return Datastructures.GeometricObject.Sphere.newBuilder()
                .setCenter(Datastructures.Vec3.newBuilder()
                        .setX(center.getX())
                        .setY(center.getY())
                        .setZ(center.getZ())
                        .build())
                .setRadius(radius)
                .build();
    }

    /**
     * Creates a new GeometricObject that holds a Plane data structure
     *
     * @param plane the plane to be wrapped by the geometric object
     * @return a geometric object holding the plane
     */
    public static Datastructures.GeometricObject toGeometricObject(Datastructures.GeometricObject.Plane plane) {
        return Datastructures.GeometricObject.newBuilder().setPlane(plane).build();
    }

    /**
     * Creates a new GeometricObject that holds a Sphere data structure
     *
     * @param sphere the sphere to be wrapped by the geometric object
     * @return a geometric object holding the sphere
     */
    public static Datastructures.GeometricObject toGeometricObject(Datastructures.GeometricObject.Sphere sphere) {
        return Datastructures.GeometricObject.newBuilder().setSphere(sphere).build();
    }
}
