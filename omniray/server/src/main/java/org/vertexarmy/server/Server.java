package org.vertexarmy.server;

import com.google.protobuf.InvalidProtocolBufferException;
import org.zeromq.ZMQ;

import java.util.ArrayList;
import java.util.List;

import static org.vertexarmy.omniray.server.protocol.Protocol.Reply;
import static org.vertexarmy.omniray.server.protocol.Protocol.Request;

/**
 * User: Alex
 * Date: 1/3/14
 */
public class Server {
    private static final String BIND_ADDRESS = "tcp://*:5555";
    private final List<String> workerIds = new ArrayList<String>();
    private ZMQ.Context context;
    private ZMQ.Socket socket;

    public void start() {
        context = ZMQ.context(1);

        socket = context.socket(ZMQ.REP);
        socket.bind(BIND_ADDRESS);

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
            return Request.parseFrom(socket.recv(0));
        } catch (InvalidProtocolBufferException e) {
            return null;
        }
    }

    private void sendReply(final Reply reply) {
        socket.send(reply.toByteArray(), 0);
    }

    private Reply handleRequest(Request request) {
        Reply.Builder replyBuilder = Reply.newBuilder();

        // handle invalid requests
        if (request == null) {
            return replyBuilder
                    .setType(Reply.Type.ERROR)
                    .setError(Reply.Error.newBuilder()
                            .setMessage("Invalid request")
                            .build())
                    .build();
        }

        switch (request.getType()) {
            case CONNECT:
                return handleConnectRequest(request);
            case PEER_LIST:
                return handlePeerListRequest();
            case PING:
                return handlePingRequest();
            case NEW_TASK:
            case PROGRESS_REPORT:
            case TASK_RESULT:
                return Reply.getDefaultInstance();
        }
        return Reply.getDefaultInstance();
    }

    private Reply handleConnectRequest(Request request) {
        String id = request.getConnect().getId();
        Reply reply = Reply.newBuilder()
                .setType(Reply.Type.CONNECT)
                .build();

        if (workerIds.contains(id)) {
            reply.toBuilder().setConnect(Reply.Connect.newBuilder().setAccepted(false).build()).build();
        } else {
            workerIds.add(request.getConnect().getId());
            reply.toBuilder().setConnect(Reply.Connect.newBuilder().setAccepted(true).build()).build();
        }

        return reply;
    }

    private Reply handlePeerListRequest() {
        return Reply.newBuilder()
                .setType(Reply.Type.PEER_LIST)
                .setPeerList(Reply.PeerList.newBuilder()
                        .addAllPeers(workerIds)
                        .build())
                .build();
    }

    private Reply handlePingRequest() {
        return Reply.newBuilder().setType(Reply.Type.PONG).build();
    }
}
