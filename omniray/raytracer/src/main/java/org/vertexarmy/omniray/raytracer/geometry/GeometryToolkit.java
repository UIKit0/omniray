package org.vertexarmy.omniray.raytracer.geometry;

import org.vertexarmy.omniray.jglm.Vec3;
import org.vertexarmy.omniray.raytracer.Datastructures;

/**
 * User: Alex
 * Date: 1/17/14
 */
public class GeometryToolkit {
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

    public static Datastructures.GeometricObject toGeometricObject(Datastructures.GeometricObject.Plane plane) {
        return Datastructures.GeometricObject.newBuilder().setPlane(plane).build();
    }

    public static Datastructures.GeometricObject toGeometricObject(Datastructures.GeometricObject.Sphere sphere) {
        return Datastructures.GeometricObject.newBuilder().setSphere(sphere).build();
    }
}
