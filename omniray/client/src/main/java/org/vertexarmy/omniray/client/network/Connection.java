package org.vertexarmy.omniray.client.network;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.Getter;
import org.apache.log4j.Logger;
import org.vertexarmy.omniray.server.protocol.Protocol;
import org.zeromq.ZMQ;

/**
 * User: Alex
 * Date: 1/20/14
 */
public class Connection {
    private final ZMQ.Context context;
    private ZMQ.Socket requestSocket;

    private String serverHost;

    @Getter
    private boolean connected;

    private final Logger logger;

    private final Protocol.Request connectRequest = Protocol.Request.newBuilder().setType(Protocol.Request.Type.REQUEST_CONNECTION).build();

    public Connection() {
        context = ZMQ.context(1);

        requestSocket = context.socket(ZMQ.REQ);

        logger = Logger.getLogger(getClass());

//        requestSocket.connect("tcp://localhost:5555");
//
//        String[] users = {"shiki", "dru", "anon"};
//        for (String user : users) {
//            Request request = Request.newBuilder()
//                    .setType(Request.Type.CONNECT)
//                    .setConnect(Request.Connect.newBuilder()
//                            .setId(user)
//                            .build())
//                    .build();
//            requestSocket.send(request.toByteArray(), 0);
//
//            Reply reply = Reply.parseFrom(requestSocket.recv(0));
//            assert (reply.getConnect().hasAccepted());
//        }
//
//        Request request = Request.newBuilder().setType(Request.Type.PEER_LIST).build();
//        requestSocket.send(request.toByteArray(), 0);
//
//        Reply reply = Reply.parseFrom(requestSocket.recv(0));
//
//        for (String peer : reply.getPeerList().getPeersList()) {
//            System.out.println(peer);
//        }
//
//        requestSocket.close();
//        context.term();
    }

    public boolean attemptConnection(String host) {
        if (connected) {
            return false;
        }

        logger.info("Attempting to connect to " + host);

        requestSocket.connect("tcp://" + host);
        sendRequest(connectRequest);

        ZMQ.Poller poller = new ZMQ.Poller(1);
        poller.register(requestSocket, ZMQ.Poller.POLLIN);

        int value = poller.poll(3 * 1000L);
        if (value == 1) {
            logger.info("Connection successful.");
            serverHost = host;
            connected = true;
            acceptReply();
        } else {
            logger.info("Timeout connecting to host");
            requestSocket.close();
            requestSocket = context.socket(ZMQ.REQ);
        }
        return connected;
    }

    public void sendRequest(Protocol.Request request) {
        requestSocket.send(request.toByteArray(), 0);
    }

    public Protocol.Reply acceptReply() {
        try {
            return Protocol.Reply.parseFrom(requestSocket.recv());
        } catch (InvalidProtocolBufferException e) {
            return null;
        }
    }

    public void disconnect() {
        if (connected) {
            logger.info("Disconnected from " + serverHost);
            connected = false;
        }
    }
}
