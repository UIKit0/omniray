package org.vertexarmy.omniray.server;

import org.apache.log4j.PropertyConfigurator;

import java.util.Properties;

public class Launcher {
    static {
        Properties log4jProperties = new Properties();
        log4jProperties.setProperty("log4j.rootLogger", "DEBUG, APPENDER");
        log4jProperties.setProperty("log4j.appender.APPENDER", "org.apache.log4j.ConsoleAppender");
        log4jProperties.setProperty("log4j.appender.APPENDER.layout", "org.apache.log4j.PatternLayout");
        log4jProperties.setProperty("log4j.appender.APPENDER.layout.conversionPattern", "%d{HH:mm:ss.SSS} %-5p [%t] %c{1}: %m%n");
        PropertyConfigurator.configure(log4jProperties);
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server();

        System.out.println("Starting Server");
        server.start();

        System.out.println("Stopping Server");
        server.stop();
    }
}