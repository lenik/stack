package com.bee32.plover.servlet.rabbits;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;

import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

public class RabbitServletContext
        extends OverlappedContext {

    public RabbitServletContext() {
        super(Context.SESSIONS | Context.SECURITY);
    }

    public synchronized void addInitParam(String name, String value) {
        Map<String, String> initParams = getInitParams();
        if (initParams == null) {
            initParams = new HashMap<String, String>();
            setInitParams(initParams);
        }
        initParams.put(name, value);
    }

    @Override
    public ServletHolder addServlet(Class servlet, String pathSpec) {
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
