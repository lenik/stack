package com.bee32.plover.servlet.rabbits;

import java.net.InetAddress;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.EventListener;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;

import org.eclipse.jetty.io.ByteArrayBuffer;
import org.eclipse.jetty.server.LocalConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.Attributes;

/**
 * Jetty Dependency Graph:
 *
 * <code>
   -tester
      -webapp
        -servlet
           -security
               -server
                   -continuation
                   -http
                       -io
                           -util
        -xml
           -util
 * </code>
 */
public class RabbitServer {

    private Server server;
    private LocalConnector localConnector;
    private RabbitServletContextHandler servletContextHandler;

    public RabbitServer() {
        server = new Server();
        server.setSendServerVersion(false);

        localConnector = new LocalConnector();
        localConnector.setPort(getPort());
        server.addConnector(localConnector);

        servletContextHandler = new RabbitServletContextHandler(this);
        server.setHandler(servletContextHandler);
    }

    public static RabbitServer getInstanceFromContext(ServletContext servletContext) {
        if (!(servletContext instanceof ContextHandler.Context))
            return null;

        ContextHandler.Context sContext = (ContextHandler.Context) servletContext;
        ContextHandler contextHandler = sContext.getContextHandler();
        if (!(contextHandler instanceof RabbitServletContextHandler))
            return null;

        RabbitServletContextHandler rsc = (RabbitServletContextHandler) contextHandler;
        RabbitServer rs = rsc.getRabbitServer();
        return rs;
    }

    public void start()
            throws Exception {
        server.start();
    }

    public void stop()
            throws Exception {
        if (server != null)
            server.stop();
    }

    protected int getPort() {
        return 0;
    }

    public RabbitServletContextHandler getServletContextHandler() {
        return servletContextHandler;
    }

    public String getResponses(String rawRequests)
            throws Exception {
        reopen(localConnector);
        String responses = localConnector.getResponses(rawRequests);
        return responses;
    }

    public String getResponses(String rawRequests, LocalConnector connector)
            throws Exception {
        reopen(localConnector);
        String responses = connector.getResponses(rawRequests);
        return responses;
    }

    public ByteArrayBuffer getResponses(ByteArrayBuffer rawRequests)
            throws Exception {
        reopen(localConnector);
        ByteArrayBuffer responses = localConnector.getResponses(rawRequests, false);
        return responses;
    }

    public ByteArrayBuffer getResponses(ByteArrayBuffer rawRequests, LocalConnector connector)
            throws Exception {
        reopen(localConnector);
        ByteArrayBuffer responses = connector.getResponses(rawRequests, false);
        return responses;
    }

    static void reopen(LocalConnector connector) {
        // if (connector.isStopped())
        // connector.open();
    }

    public synchronized String createSocketConnector(boolean boundToLocalhostonly)
            throws Exception {
        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(getPort());

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
        servletContextHandler.setEventListeners(eventListeners);
    }

    public void addEventListener(EventListener listener) {
        servletContextHandler.addEventListener(listener);
    }

    public FilterHolder addFilter(Class<? extends Filter> filterClass, String pathSpec, DispatcherType... dispatches) {
        EnumSet<DispatcherType> set = EnumSet.noneOf(DispatcherType.class);
        for (DispatcherType dispatch : dispatches)
            set.add(dispatch);
        return servletContextHandler.addFilter(filterClass, pathSpec, set);
    }

    public FilterHolder addFilter(String filterClass, String pathSpec, DispatcherType... dispatches) {
        EnumSet<DispatcherType> set = EnumSet.noneOf(DispatcherType.class);
        for (DispatcherType dispatch : dispatches)
            set.add(dispatch);
        return servletContextHandler.addFilter(filterClass, pathSpec, set);
    }

    /**
     * Add servlet without mapping, and load-on-startup.
     */
    public ServletHolder addServlet(Class<? extends Servlet> servlet) {
        return servletContextHandler.addServlet(servlet, null);
    }

    public ServletHolder addServlet(Class<? extends Servlet> servlet, String pathSpec) {
        return servletContextHandler.addServlet(servlet, pathSpec);
    }

    /**
     * Add servlet without mapping, and load-on-startup.
     */
    public ServletHolder addServlet(String className) {
        return servletContextHandler.addServlet(className, null);
    }

    public ServletHolder addServlet(String className, String pathSpec) {
        return servletContextHandler.addServlet(className, pathSpec);
    }

    public Attributes getAttributes() {
        return servletContextHandler.getAttributes();
    }

    public Enumeration<String> getAttributeNames() {
        return servletContextHandler.getAttributeNames();
    }

    public Object getAttribute(String name) {
        return servletContextHandler.getAttribute(name);
    }

    public void setAttribute(String name, Object value) {
        servletContextHandler.setAttribute(name, value);
    }

    public void setClassLoader(ClassLoader classLoader) {
        servletContextHandler.setClassLoader(classLoader);
    }

    public void setContextPath(String contextPath) {
        servletContextHandler.setContextPath(contextPath);
    }

}
