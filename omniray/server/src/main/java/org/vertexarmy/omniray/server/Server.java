package org.vertexarmy.omniray.server;

import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.log4j.Logger;
import org.vertexarmy.omniray.raytracer.Datastructures;
import org.zeromq.ZMQ;

import java.util.List;

import static org.vertexarmy.omniray.server.protocol.Protocol.Reply;
import static org.vertexarmy.omniray.server.protocol.Protocol.Request;

/**
 * User: Alex
 * Date: 1/3/14
 */
public class Server {
    private static final String BIND_ADDRESS = "tcp://*:5555";
    private final TaskManager taskManager = new TaskManager();
    private ZMQ.Context context;
    private ZMQ.Socket socket;
    private Logger logger;

    public void start() {
        context = ZMQ.context(1);

        socket = context.socket(ZMQ.REP);
        socket.bind(BIND_ADDRESS);

        logger = org.apache.log4j.Logger.getLogger(getClass());

        while (!Thread.currentThread().isInterrupted()) {
            Request request = awaitRequest();
            sendReply(handleRequest(request));
        }
    }

    public void stop() {
        socket.close();
        context.term();
    }

    private Request awaitRequest() {
        try {
            Request request = Request.parseFrom(socket.recv(0));
            logger.info("Received request " + request.getType());
            return request;
        } catch (InvalidProtocolBufferException e) {
            return null;
        }
    }

    private void sendReply(final Reply reply) {
        socket.send(reply.toByteArray(), 0);
    }

    private Reply handleRequest(Request request) {
        if (request == null) {
            return handleInvalidRequest();
        }

        switch (request.getType()) {
            case REQUEST_CONNECTION:
                return handleConnectionRequest();
            case CLIENT_REQUEST_POST_TASK:
                return handleClientPostTaskRequest(request);
            case CLIENT_REQUEST_TASK_RESULT:
                return handleClientTaskResultRequest(request);
            case WORKER_REQUEST_NEW_TASK:
                return handleWorkerNewTaskRequest(request);
            case WORKER_TASK_FINISHED:
                return handleWorkerTaskFinishedRequest(request);
        }

        return Reply.getDefaultInstance();
    }

    private Reply handleInvalidRequest() {
        return Reply.getDefaultInstance();
    }

    private Reply handleConnectionRequest() {
        return Reply.newBuilder()
                .setType(Reply.Type.REPLY_CONNECTION)
                .build();
    }

    private Reply handleClientPostTaskRequest(Request request) {
        String taskId = taskManager.pushTask(request.getClientRequestPostTask().getTask());

        return Reply.newBuilder()
                .setType(Reply.Type.CLIENT_REPLY_POST_TASK)
                .setClientReplyPostTask(Reply.ClientReplyPostTask.newBuilder()
                        .setId(taskId))
                .build();
    }

    private Reply handleClientTaskResultRequest(Request request) {
        String taskId = request.getClientRequestTaskResult().getTaskId();
        boolean taskComplete = taskManager.isTaskComplete(taskId);

        List<Datastructures.ColorBuffer> results = taskManager.getTaskUpdates(taskId);

        return Reply.newBuilder()
                .setType(Reply.Type.CLIENT_REPLY_TASK_RESULT)
                .setClientReplyTaskResult(Reply.ClientReplyTaskResult.newBuilder()
                        .setTaskComplete(taskComplete)
                        .addAllResultBuffer(results))
                .build();
    }

    private Reply handleWorkerNewTaskRequest(Request request) {
        String taskId = taskManager.getQueuedSubtask();

        if (taskId == null) {
            return Reply.newBuilder()
                    .setType(Reply.Type.WORKER_REPLY_NEW_TASK)
                    .build();
        }

        Datastructures.Task task = taskManager.getTask(taskId);

        return Reply.newBuilder()
                .setType(Reply.Type.WORKER_REPLY_NEW_TASK)
                .setWorkerReplyNewTask(Reply.WorkerReplyNewTask.newBuilder()
                        .setTaskId(taskId)
                        .setTask(task))
                .build();
    }

    private Reply handleWorkerTaskFinishedRequest(Request request) {
        taskManager.completeTask(request.getWorkerTaskFinished().getTaskId(),
                request.getWorkerTaskFinished().getTaskResult());

        return Reply.newBuilder()
                .setType(Reply.Type.WORKER_REPLY_TASK_FINISHED)
                .build();
    }
}
