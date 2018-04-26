package com.test.jetty;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

public class JettyServer {
    private Server server;

    public void start() throws Exception {
        int maxThreads = 100;
        int minThreads = 10;
        int idleTimeout = 120;

        QueuedThreadPool threadPool = new QueuedThreadPool(maxThreads, minThreads, idleTimeout);



        server = new Server(threadPool);
        ServerConnector connector = new ServerConnector(server);

        ServletHandler servletHandler = new ServletHandler();

        connector.setPort(8090);
        server.setConnectors(new Connector[]{connector});

        server.setHandler(servletHandler);

        servletHandler.addServletWithMapping(
                AsyncServlet.class, "/heavy/async");

        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }
}