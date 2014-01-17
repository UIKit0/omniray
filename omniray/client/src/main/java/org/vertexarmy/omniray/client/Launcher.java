package org.vertexarmy.omniray.client;

import org.vertexarmy.omniray.client.ui.ConfigurationWindow;
import org.vertexarmy.omniray.client.ui.OutputWindow;
import org.vertexarmy.omniray.client.ui.Toolkit;
import org.vertexarmy.omniray.client.ui.laf.HalloweenLookAndFeel;
import org.vertexarmy.omniray.jglm.Vec3;
import org.vertexarmy.omniray.raytracer.Datastructures;
import org.vertexarmy.omniray.raytracer.Tracer;
import org.vertexarmy.omniray.raytracer.geometry.GeometryToolkit;

import javax.swing.*;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

/**
 * User: Alex
 * Date: 11/3/13
 */
public class Launcher {
    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        HalloweenLookAndFeel.install();
    }

    public static void main(String[] argv) throws IOException {
        long time;

        final OutputWindow window = new OutputWindow();
        final ConfigurationWindow configurationWindow = new ConfigurationWindow();
        Toolkit.attachFrame(window, configurationWindow);

        final Tracer rayTracer = new Tracer();

        // create view plane
        final Datastructures.ViewPlane viewPlane = createViewPlane();

        // create settings
        final Datastructures.Settings settings = createSettings();

        // build a world to render
        time = Toolkit.currentTime();
        final Datastructures.World world = createMultipleSpheresWorld();
        System.out.println("Prepared world in " + Toolkit.diffTime(time) + "ms.");

        configurationWindow.registerListener(new ConfigurationWindow.Listener() {
            @Override
            public void onRenderRequested() {
                renderWorld(rayTracer, createTask(viewPlane, settings, world), window);
            }

            @Override
            public void onClearRequested() {
                window.getCanvas().clear();
                window.repaint();
            }
        });
    }

    private static Datastructures.Task createTask(Datastructures.ViewPlane viewPlane, Datastructures.Settings settings, Datastructures.World world) {
        return Datastructures.Task.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setViewPlane(viewPlane)
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
                .setX(0)
                .setY(0)
                .setWidth(800)
                .setHeight(600)
                .build();
    }

    private static void renderWorld(Tracer tracer, Datastructures.Task task, OutputWindow window) {
        long time = Toolkit.currentTime();
        tracer.render(task, window.getCanvas().getImageBuilder());
        System.out.println("Rendered world in " + Toolkit.diffTime(time) + "ms.");
    }

    private static Datastructures.World createSingleSphereWorld() {
        return Datastructures.World.newBuilder().
                addGeometricObject(
                        GeometryToolkit.toGeometricObject(GeometryToolkit.buildSphere(new Vec3(0, 100, 200), 160)))
                .build();
    }

    private static Datastructures.World createMultipleSpheresWorld() {
        final float MAIN_SPHERE_RADIUS = 140;
        final int MINOR_SPHERE_COUNT = 240;

        Datastructures.World.Builder worldBuilder = Datastructures.World.newBuilder();
        worldBuilder.addGeometricObject(GeometryToolkit.toGeometricObject(
                GeometryToolkit.buildSphere(new Vec3(0, 0, 200), MAIN_SPHERE_RADIUS)));

        Random random = new Random();

        for (int i = 0; i < MINOR_SPHERE_COUNT; ++i) {
            double a1 = random.nextFloat() * Math.PI * 2;
            double a2 = random.nextFloat() * Math.PI * 2;
            Vec3 origin = new Vec3(
                    (float) (Math.cos(a2) * Math.sin(a1) * MAIN_SPHERE_RADIUS),
                    (float) (Math.sin(a2) * MAIN_SPHERE_RADIUS),
                    (float) (Math.cos(a2) * Math.cos(a1) * MAIN_SPHERE_RADIUS) + 200);
            float r = (random.nextFloat() + 0.2f) * 30;

            worldBuilder.addGeometricObject(GeometryToolkit.toGeometricObject(
                    GeometryToolkit.buildSphere(origin, r)));
        }
        return worldBuilder.build();
    }
}
