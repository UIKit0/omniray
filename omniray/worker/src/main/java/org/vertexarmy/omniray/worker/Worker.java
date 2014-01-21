package org.vertexarmy.omniray.worker;

import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.log4j.Logger;
import org.vertexarmy.omniray.raytracer.Datastructures;
import org.vertexarmy.omniray.raytracer.Tracer;
import org.vertexarmy.omniray.server.protocol.Protocol;
import org.zeromq.ZMQ;


/**
 * User: Alex
 * Date: 1/21/14
 */
public class Worker {
    private final ZMQ.Context context;
    private final ZMQ.Socket socket;
    private Logger logger;

    public Worker(final String serverHost) {
        context = ZMQ.context(1);
        socket = context.socket(ZMQ.REQ);

        socket.connect(serverHost);

        logger = Logger.getLogger(Worker.class);
    }

    public void start() {
        Tracer tracer = new Tracer();

        while (!Thread.currentThread().isInterrupted()) {
            logger.info("Requesting a new task from dispatcher server.");
            socket.send(Protocol.Request.newBuilder().setType(Protocol.Request.Type.WORKER_REQUEST_NEW_TASK).build().toByteArray());
            Protocol.Reply reply = awaitReply();
            if (reply.hasWorkerReplyNewTask()) {
                logger.info("Received a new task: " + reply.getWorkerReplyNewTask().getTaskId());
                logger.info("Task size is: " + reply.getWorkerReplyNewTask().getTask().getRenderSection().toString());

                // prepare for work
                String taskId = reply.getWorkerReplyNewTask().getTaskId();
                Datastructures.Task task = reply.getWorkerReplyNewTask().getTask();
                ResultBuilder resultBuilder = new ResultBuilder(task.getRenderSection().getWidth(), task.getRenderSection().getHeight());

                // work
                tracer.render(task, resultBuilder);
                Datastructures.ColorBuffer result = resultBuilder.getColorBufferResult(task.getRenderSection());

                // return the result
                logger.info("Work finished. Sending the results back to the server");
                socket.send(Protocol.Request.newBuilder()
                        .setType(Protocol.Request.Type.WORKER_TASK_FINISHED)
                        .setWorkerTaskFinished(Protocol.Request.WorkerTaskFinished.newBuilder()
                                .setTaskId(taskId)
                                .setTaskResult(result))
                        .build()
                        .toByteArray());

                awaitReply();
            } else {
                logger.info("No task received from server. Trying again in 5s.");
                sleep(5000);
            }
        }
    }

    private void sleep(int miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException ignored) {
        }
    }

    public void stop() {
        socket.close();
        context.term();
    }

    private Protocol.Reply awaitReply() {
        try {
            Protocol.Reply reply = Protocol.Reply.parseFrom(socket.recv(0));
            return reply;
        } catch (InvalidProtocolBufferException e) {
            return null;
        }
    }
}
