package org.vertexarmy.omniray.client;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.vertexarmy.omniray.client.network.Connection;
import org.vertexarmy.omniray.client.ui.ConfigurationWindow;
import org.vertexarmy.omniray.client.ui.OutputWindow;
import org.vertexarmy.omniray.client.ui.Toolkit;
import org.vertexarmy.omniray.client.ui.laf.HalloweenLookAndFeel;
import org.vertexarmy.omniray.jglm.Vec3;
import org.vertexarmy.omniray.raytracer.Datastructures;
import org.vertexarmy.omniray.raytracer.Tracer;
import org.vertexarmy.omniray.raytracer.geometry.GeometryToolkit;
import org.vertexarmy.omniray.server.protocol.Protocol;

import javax.swing.*;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Random;

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

        Properties log4jProperties = new Properties();
        log4jProperties.setProperty("log4j.rootLogger", "DEBUG, APPENDER");
        log4jProperties.setProperty("log4j.appender.APPENDER", "org.apache.log4j.ConsoleAppender");
        log4jProperties.setProperty("log4j.appender.APPENDER.layout", "org.apache.log4j.PatternLayout");
        log4jProperties.setProperty("log4j.appender.APPENDER.layout.conversionPattern", "%d{HH:mm:ss.SSS} %-5p [%t] %c{1}: %m%n");
        PropertyConfigurator.configure(log4jProperties);
    }

    final static Connection connection = new Connection();
    final static ConfigurationWindow configurationWindow = new ConfigurationWindow(connection);
    final static Logger logger = Logger.getLogger(Launcher.class);
    final static OutputWindow window = new OutputWindow();

    static String taskId;

    public static void main(String[] argv) throws IOException {

        long time;

        Toolkit.attachFrame(window, configurationWindow);

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
                Datastructures.Task renderTask = createTask(viewPlane, settings, world);
                if (connection.isConnected()) {

                    // send the task to the server
                    connection.sendRequest(Protocol.Request.newBuilder()
                            .setType(Protocol.Request.Type.CLIENT_REQUEST_POST_TASK)
                            .setClientRequestPostTask(Protocol.Request.ClientRequestPostTask.newBuilder()
                                    .setTask(renderTask))
                            .build());

                    taskId = connection.acceptReply().getClientReplyPostTask().getId();
                    configurationWindow.setRenderButtonEnabled(false);

                    awaitResults();
                } else {
                    renderWorld(renderTask, window);
                }
            }

            @Override
            public void onClearRequested() {
                window.getCanvas().clear();
                window.repaint();
            }
        });
    }

    private static void awaitResults() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                window.getCanvas().getImageBuilder().begin();
                while (true) {
                    connection.sendRequest(Protocol.Request.newBuilder()
                            .setType(Protocol.Request.Type.CLIENT_REQUEST_TASK_RESULT)
                            .setClientRequestTaskResult(Protocol.Request.ClientRequestTaskResult.newBuilder()
                                    .setTaskId(taskId))
                            .build());

                    Protocol.Reply reply = connection.acceptReply();

                    List<Datastructures.ColorBuffer> results = reply.getClientReplyTaskResult().getResultBufferList();

                    if (results.size() > 0) {
                        logger.info("Received " + results.size() + " results from server.");
                    }

                    for (Datastructures.ColorBuffer result : results) {
                        applyResult(result);
                    }

                    // end if the task is complete
                    if (reply.getClientReplyTaskResult().getTaskComplete()) {
                        logger.info("Received all results from server. Task is finished.");
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                configurationWindow.setRenderButtonEnabled(true);
                            }
                        });
                        window.getCanvas().getImageBuilder().end();
                        return;
                    }

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ignored) {
                    }
                }
            }

            private void applyResult(Datastructures.ColorBuffer result) {
                Datastructures.Rect resultSize = result.getSize();

                int offset = 0;
                for (int i = resultSize.getX(); i < resultSize.getWidth() + resultSize.getX(); ++i) {
                    for (int j = resultSize.getY(); j < resultSize.getHeight() + resultSize.getY(); ++j) {
                        window.getCanvas().getImageBuilder().setColor(i, j, result.getValue(offset++));
                    }
                }
                window.repaint();
            }

        }).start();
    }

    private static Datastructures.Task createTask(Datastructures.ViewPlane viewPlane, Datastructures.Settings settings, Datastructures.World world) {
        return Datastructures.Task.newBuilder()
                .setViewPlane(viewPlane)
                .setSettings(settings)
                .setWorld(world)
                .setRenderSection(Datastructures.Rect.newBuilder().setX(0).setY(0).setWidth(800).setHeight(600))
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
                        .setX(-400)
                        .setY(-300)
                        .setWidth(800)
                        .setHeight(600)
                        .build())
                .build();
    }

    private static void renderWorld(Datastructures.Task task, OutputWindow window) {
        final Tracer rayTracer = new Tracer();

        long time = Toolkit.currentTime();
        rayTracer.render(task, window.getCanvas().getImageBuilder());
        System.out.println("Rendered world in " + Toolkit.diffTime(time) + "ms.");
    }

    private static Datastructures.World createSingleSphereWorld() {
        return Datastructures.World.newBuilder().
                addGeometricObject(
                        GeometryToolkit.toGeometricObject(GeometryToolkit.buildSphere(new Vec3(0, 0, 200), 160)))
                .build();
    }

    private static Datastructures.World createMultipleSpheresWorld() {
        final float MAIN_SPHERE_RADIUS = 140;
        final int MINOR_SPHERE_COUNT = 120;

        Datastructures.World.Builder worldBuilder = Datastructures.World.newBuilder();
        worldBuilder.addGeometricObject(GeometryToolkit.toGeometricObject(
                GeometryToolkit.buildSphere(new Vec3(0, 0, 200), MAIN_SPHERE_RADIUS)));

        Random random = new Random();

        for (int i = 0; i < MINOR_SPHERE_COUNT; ++i) {
            double a1 = random.nextFloat() * Math.PI - Math.PI / 2;
            double a2 = random.nextFloat() * Math.PI + Math.PI / 2;
            Vec3 origin = new Vec3(
                    (float) (Math.cos(a2) * Math.sin(a1) * MAIN_SPHERE_RADIUS),
                    (float) (Math.sin(a2) * MAIN_SPHERE_RADIUS),
                    (float) (Math.cos(a2) * Math.cos(a1) * MAIN_SPHERE_RADIUS) + 200);
            float r = (random.nextFloat() + 0.1f) * 20;

            worldBuilder.addGeometricObject(GeometryToolkit.toGeometricObject(
                    GeometryToolkit.buildSphere(origin, r)));
        }
        return worldBuilder.build();
    }
}
