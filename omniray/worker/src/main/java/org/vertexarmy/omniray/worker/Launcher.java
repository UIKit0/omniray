package org.vertexarmy.omniray.worker;

import org.apache.log4j.PropertyConfigurator;

import java.util.Properties;

/**
 * User: Alex
 * Date: 1/21/14
 */
public class Launcher {
    static {
        Properties log4jProperties = new Properties();
        log4jProperties.setProperty("log4j.rootLogger", "DEBUG, APPENDER");
        log4jProperties.setProperty("log4j.appender.APPENDER", "org.apache.log4j.ConsoleAppender");
        log4jProperties.setProperty("log4j.appender.APPENDER.layout", "org.apache.log4j.PatternLayout");
        log4jProperties.setProperty("log4j.appender.APPENDER.layout.conversionPattern", "%d{HH:mm:ss.SSS} %-5p [%t] %c{1}: %m%n");
        PropertyConfigurator.configure(log4jProperties);
    }

    private final static String SERVER_HOST = "tcp://127.0.0.1:5555";

    public static void main(String[] args) {
        String serverHost = SERVER_HOST;
        if (args.length == 1) {
            serverHost = args[0];
        }
        Worker worker = new Worker(serverHost);
        worker.start();
    }
}
