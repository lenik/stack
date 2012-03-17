package com.bee32.plover.servlet.rabbits;

import java.util.Map;

import javax.servlet.Servlet;

import org.eclipse.jetty.servlet.ServletHolder;

public class RabbitServletContextHandler
        extends OverlappedContextHandler {

    final RabbitServer rabbitServer;

    public RabbitServletContextHandler(RabbitServer rabbitServer) {
        super(SESSIONS | SECURITY);
        if (rabbitServer == null)
            throw new NullPointerException("rabbitServer");
        this.rabbitServer = rabbitServer;
    }

    public RabbitServer getRabbitServer() {
        return rabbitServer;
    }

    public synchronized void addInitParam(String name, String value) {
        Map<String, String> _initParams = getInitParams();
        _initParams.put(name, value);
    }

    @Override
    public ServletHolder addServlet(Class<? extends Servlet> servlet, String pathSpec) {
        // return super.addServlet(servlet, pathSpec);
        if (pathSpec == null) {
            ServletHolder holder = new ServletHolder(servlet);
            holder.setInitOrder(0);
            _servletHandler.addServlet(holder);
            return holder;
        } else {
            return _servletHandler.addServletWithMapping(servlet.getName(), pathSpec);
        }
    }

    @Override
    public ServletHolder addServlet(String className, String pathSpec) {
        // return super.addServlet(className, pathSpec);
        if (pathSpec == null) {
            ServletHolder holder = new ServletHolder((Servlet) null);
            holder.setName(className + "-" + holder.hashCode());
            holder.setClassName(className);
            holder.setInitOrder(0);
            _servletHandler.addServlet(holder);
            return holder;
        } else {
            return _servletHandler.addServletWithMapping(className, pathSpec);
        }
    }

}
