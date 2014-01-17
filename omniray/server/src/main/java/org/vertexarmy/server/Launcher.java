package org.vertexarmy.server;

public class Launcher {
    public static void main(String[] args) throws Exception {
        Server server = new Server();

        System.out.println("Starting Server");
        server.start();

        System.out.println("Stopping Server");
        server.stop();
    }
}