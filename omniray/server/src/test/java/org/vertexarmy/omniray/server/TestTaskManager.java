package org.vertexarmy.omniray.server;

import com.google.common.collect.Sets;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.vertexarmy.omniray.raytracer.Datastructures;

import java.util.Set;

/**
 * User: Alex
 * Date: 1/21/14
 */
public class TestTaskManager {
    @Test
    public void testTaskSplitting() {
        TaskManager taskManager = new TaskManager();

        Datastructures.Task originalTask = createTask(-400, -300, 800, 600);
        String taskId = taskManager.pushTask(originalTask);

        int subTaskCount = 0;
        String subTaskId = taskManager.getQueuedSubtask();
        while (subTaskId != null) {
            subTaskCount += 1;
            Datastructures.Task subTask = taskManager.getTask(subTaskId);

            Assert.assertEquals(subTask.getViewPlane(), originalTask.getViewPlane());
            Assert.assertEquals(subTask.getSettings(), originalTask.getSettings());
            Assert.assertEquals(subTask.getWorld(), originalTask.getWorld());

            Assert.assertEquals(100, subTask.getRenderSection().getWidth());
            Assert.assertEquals(100, subTask.getRenderSection().getHeight());

            subTaskId = taskManager.getQueuedSubtask();
        }

        Assert.assertEquals(8 * 6, subTaskCount);
    }

    public Datastructures.Task createTask(int x, int y, int width, int height) {
        return Datastructures.Task.newBuilder()
                .setWorld(Datastructures.World.getDefaultInstance())
                .setSettings(Datastructures.Settings.newBuilder()
                        .setSampler(Datastructures.Sampler.newBuilder()
                                .setSamplingTechnique(Datastructures.Sampler.SamplingTechnique.NONE)
                                .setSampleCount(1)))
                .setRenderSection(Datastructures.Rect.newBuilder()
                        .setX(x)
                        .setY(y)
                        .setWidth(width)
                        .setHeight(height))
                .setViewPlane(Datastructures.ViewPlane.newBuilder()
                        .setViewport(Datastructures.Rect.newBuilder()
                                .setX(x)
                                .setY(y)
                                .setWidth(width)
                                .setHeight(height)))
                .build();
    }
}
