package com.bee32.plover.servlet.test;

import java.net.InetAddress;
import java.util.Enumeration;
import java.util.EventListener;

import org.mortbay.io.ByteArrayBuffer;
import org.mortbay.jetty.LocalConnector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.FilterHolder;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.util.Attributes;

public class RabbitServer {

    private final Server server;
    private final LocalConnector localConnector;
    private final RabbitServletContext servletContext;

    public RabbitServer() {
        server = new Server();
        localConnector = new LocalConnector();
        servletContext = new RabbitServletContext();

        server.setSendServerVersion(false);
        server.addConnector(localConnector);
        server.addHandler(servletContext);
    }

    public void start()
            throws Exception {
        server.start();
    }

    public void stop()
            throws Exception {
        server.stop();
    }

    public RabbitServletContext getServletContext() {
        return servletContext;
    }

    public String getResponses(String rawRequests)
            throws Exception {
        localConnector.reopen();
        String responses = localConnector.getResponses(rawRequests);
        return responses;
    }

    public String getResponses(String rawRequests, LocalConnector connector)
            throws Exception {
        connector.reopen();
        String responses = connector.getResponses(rawRequests);
        return responses;
    }

    public ByteArrayBuffer getResponses(ByteArrayBuffer rawRequests)
            throws Exception {
        localConnector.reopen();
        ByteArrayBuffer responses = localConnector.getResponses(rawRequests, false);
        return responses;
    }

    public ByteArrayBuffer getResponses(ByteArrayBuffer rawRequests, LocalConnector connector)
            throws Exception {
        connector.reopen();
        ByteArrayBuffer responses = connector.getResponses(rawRequests, false);
        return responses;
    }

    public synchronized String createSocketConnector(boolean boundToLocalhostonly)
            throws Exception {
        SelectChannelConnector connector = new SelectChannelConnector();

        if (boundToLocalhostonly)
            connector.setHost("127.0.0.1");

        server.addConnector(connector);

        if (server.isStarted())
            connector.start();
        else
            connector.open();

        String boundHost = boundToLocalhostonly ? "127.0.0.1" : InetAddress.getLocalHost().getHostAddress();
        int boundPort = connector.getLocalPort();

        return "http://" + boundHost + ":" + boundPort;
    }

    public LocalConnector createLocalConnector()
            throws Exception {
        synchronized (this) {
            LocalConnector connector = new LocalConnector();
            server.addConnector(connector);

            if (server.isStarted())
                connector.start();

            return connector;
        }
    }

    // Facade to servlet context.

    public void setEventListeners(EventListener[] eventListeners) {
        servletContext.setEventListeners(eventListeners);
    }

    public void addEventListener(EventListener listener) {
        servletContext.addEventListener(listener);
    }

    public FilterHolder addFilter(Class<?> filterClass, String pathSpec, int dispatches) {
        return servletContext.addFilter(filterClass, pathSpec, dispatches);
    }

    public FilterHolder addFilter(String filterClass, String pathSpec, int dispatches) {
        return servletContext.addFilter(filterClass, pathSpec, dispatches);
    }

    public ServletHolder addServlet(Class<?> servlet, String pathSpec) {
        return servletContext.addServlet(servlet, pathSpec);
    }

    public ServletHolder addServlet(String className, String pathSpec) {
        return servletContext.addServlet(className, pathSpec);
    }

    public Attributes getAttributes() {
        return servletContext.getAttributes();
    }

    public Enumeration<String> getAttributeNames() {
        return servletContext.getAttributeNames();
    }

    public Object getAttribute(String name) {
        return servletContext.getAttribute(name);
    }

    public void setAttribute(String name, Object value) {
        servletContext.setAttribute(name, value);
    }

    public void setClassLoader(ClassLoader classLoader) {
        servletContext.setClassLoader(classLoader);
    }

    public void setContextPath(String contextPath) {
        servletContext.setContextPath(contextPath);
    }

}
