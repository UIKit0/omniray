package org.vertexarmy.server;

import org.zeromq.ZMQ;

import static org.vertexarmy.omniray.server.protocol.Protocol.Reply;
import static org.vertexarmy.omniray.server.protocol.Protocol.Request;

public class Client {

    public static void main(String[] args) throws Exception {
        ZMQ.Context context = ZMQ.context(1);

        ZMQ.Socket requester = context.socket(ZMQ.REQ);
        requester.connect("tcp://localhost:5555");

        String[] users = {"shiki", "dru", "anon"};
        for (String user : users) {
            Request request = Request.newBuilder()
                    .setType(Request.Type.CONNECT)
                    .setConnect(Request.Connect.newBuilder()
                            .setId(user)
                            .build())
                    .build();
            requester.send(request.toByteArray(), 0);

            Reply reply = Reply.parseFrom(requester.recv(0));
            assert (reply.getConnect().hasAccepted());
        }

        Request request = Request.newBuilder().setType(Request.Type.PEER_LIST).build();
        requester.send(request.toByteArray(), 0);

        Reply reply = Reply.parseFrom(requester.recv(0));

        for (String peer : reply.getPeerList().getPeersList()) {
            System.out.println(peer);
        }

        requester.close();
        context.term();
    }
}