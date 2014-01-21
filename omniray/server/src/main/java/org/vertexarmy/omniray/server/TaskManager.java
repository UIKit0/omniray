package org.vertexarmy.omniray.server;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import org.vertexarmy.omniray.jglm.support.FastMath;
import org.vertexarmy.omniray.raytracer.Datastructures;

import java.util.*;

/**
 * User: Alex
 * Date: 1/20/14
 */
public class TaskManager {
    private static final int TASK_CHUNK_SIZE = 100;

    private final Multimap<String, String> tasks = LinkedListMultimap.create();
    private final Map<String, Datastructures.Task> subTasks = new HashMap<String, Datastructures.Task>();
    private final Set<String> completedSubTasks = new HashSet<String>();
    private final Set<String> completedTasks = new HashSet<String>();
    private final Set<String> updatedSubTasks = new HashSet<String>();
    private final Set<String> dispatchedSubTasks = new HashSet<String>();
    private final Map<String, Datastructures.ColorBuffer> subTaskResults = new HashMap<String, Datastructures.ColorBuffer>();

    public Datastructures.Task getTask(String taskId) {
        return subTasks.get(taskId);
    }

    public String pushTask(Datastructures.Task task) {
        String taskId = UUID.randomUUID().toString();
        splitTask(task, taskId);
        return taskId;
    }

    public String getQueuedSubtask() {
        for (String subtaskId : tasks.values()) {
            if (!completedSubTasks.contains(subtaskId) && !dispatchedSubTasks.contains(subtaskId)) {
                dispatchedSubTasks.add(subtaskId);
                return subtaskId;
            }
        }
        return null;
    }

    public void completeTask(String taskId, Datastructures.ColorBuffer result) {
        completedSubTasks.add(taskId);
        dispatchedSubTasks.remove(taskId);
        subTaskResults.put(taskId, result);
    }

    public List<Datastructures.ColorBuffer> getTaskUpdates(String taskId) {
        List<Datastructures.ColorBuffer> results = new ArrayList<Datastructures.ColorBuffer>();

        if (completedTasks.contains(taskId)) {
            return results;
        }

        int incompleteSubtasks = 0;

        for (String subtaskId : tasks.get(taskId)) {
            if (completedSubTasks.contains(subtaskId)) {
                if (!updatedSubTasks.contains(subtaskId)) {
                    updatedSubTasks.add(subtaskId);
                    results.add(subTaskResults.get(subtaskId));
                }
            } else {
                incompleteSubtasks += 1;
            }
        }

        if (incompleteSubtasks == 0) {
            completedTasks.add(taskId);
        }

        return results;
    }

    public boolean isTaskComplete(String taskId) {
        return completedTasks.contains(taskId);
    }

    private void splitTask(Datastructures.Task task, String taskId) {
        int taskWidth = task.getViewPlane().getViewport().getWidth();
        int taskHeight = task.getViewPlane().getViewport().getHeight();

        for (int i = 0; i < taskWidth; i += TASK_CHUNK_SIZE) {
            for (int j = 0; j < taskHeight; j += TASK_CHUNK_SIZE) {
                Datastructures.Rect renderSection = Datastructures.Rect.newBuilder()
                        .setX(i)
                        .setY(j)
                        .setWidth(FastMath.min(TASK_CHUNK_SIZE, taskWidth - i))
                        .setHeight(FastMath.min(TASK_CHUNK_SIZE, taskHeight - j))
                        .build();

                Datastructures.Task subtask = task.toBuilder()
                        .setRenderSection(renderSection)
                        .build();

                String subtaskId = UUID.randomUUID().toString();

                subTasks.put(subtaskId, subtask);
                tasks.put(taskId, subtaskId);
            }
        }
    }
}
