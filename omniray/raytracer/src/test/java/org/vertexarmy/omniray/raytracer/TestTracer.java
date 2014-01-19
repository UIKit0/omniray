package org.vertexarmy.omniray.raytracer;

import org.mockito.Mockito;
import org.testng.annotations.Test;
import org.vertexarmy.omniray.jglm.Vec3;
import org.vertexarmy.omniray.raytracer.geometry.GeometryToolkit;

import java.util.UUID;

/**
 * User: Alex
 * Date: 1/19/14
 */
public class TestTracer {
    private final static int VIEWPORT_WIDTH = 50;
    private final static int VIEWPORT_HEIGHT = 50;

    @Test
    public void testRayTracer() {
        // create a ray tracer
        final Tracer tracer = new Tracer();

        // create view plane
        final Datastructures.ViewPlane viewPlane = createViewPlane();

        // create settings
        final Datastructures.Settings settings = createSettings();

        // build a world to render
        final Datastructures.World world = createSingleSphereWorld();

        final ImageBuilder mockImageBuilder = Mockito.mock(ImageBuilder.class);

        tracer.render(createTask(viewPlane, settings, world), mockImageBuilder);

        Mockito.verify(mockImageBuilder, Mockito.times(1)).begin();
        Mockito.verify(mockImageBuilder, Mockito.times(1)).end();
        Mockito.verify(mockImageBuilder, Mockito.times(VIEWPORT_WIDTH * VIEWPORT_HEIGHT)).setColor(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());

    }

    private static Datastructures.Task createTask(Datastructures.ViewPlane viewPlane, Datastructures.Settings settings, Datastructures.World world) {
        return Datastructures.Task.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setViewPlane(viewPlane)
                .setRenderSection(viewPlane.getViewport())
                .setSettings(settings)
                .setWorld(world)
                .build();
    }

    private static Datastructures.Settings createSettings() {
        return Datastructures.Settings.newBuilder()
                .setSampler(Datastructures.Sampler.newBuilder()
                        .setSamplingTechnique(Datastructures.Sampler.SamplingTechnique.NONE))
                .build();
    }

    private static Datastructures.ViewPlane createViewPlane() {
        return Datastructures.ViewPlane.newBuilder()
                .setViewport(Datastructures.Rect.newBuilder()
                        .setX(-VIEWPORT_WIDTH / 2)
                        .setY(-VIEWPORT_HEIGHT / 2)
                        .setWidth(VIEWPORT_WIDTH / 2)
                        .setHeight(VIEWPORT_HEIGHT / 2)
                        .build())
                .build();
    }

    private static Datastructures.World createSingleSphereWorld() {
        return Datastructures.World.newBuilder().
                addGeometricObject(
                        GeometryToolkit.toGeometricObject(GeometryToolkit.buildSphere(new Vec3(0, 0, 200), 160)))
                .build();
    }
}
