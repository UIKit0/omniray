package org.vertexarmy.omniray.server;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.vertexarmy.omniray.raytracer.Datastructures;

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

        int subtaskCount = 0;
        String subtaskId = taskManager.getQueuedSubtask();
        while (subtaskId != null) {
            subtaskCount += 1;
            Datastructures.Task subtask = taskManager.getTask(subtaskId);

            Assert.assertEquals(subtask.getViewPlane(), originalTask.getViewPlane());
            Assert.assertEquals(subtask.getSettings(), originalTask.getSettings());
            Assert.assertEquals(subtask.getWorld(), originalTask.getWorld());

            Assert.assertEquals(100, subtask.getRenderSection().getWidth());
            Assert.assertEquals(100, subtask.getRenderSection().getHeight());

            subtaskId = taskManager.getQueuedSubtask();
        }

        Assert.assertEquals(8 * 6, subtaskCount);
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
