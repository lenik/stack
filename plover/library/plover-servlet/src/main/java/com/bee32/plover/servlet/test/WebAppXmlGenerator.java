package com.bee32.plover.servlet.test;

import java.io.PrintStream;

import javax.servlet.Servlet;

import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.bee32.plover.servlet.rabbits.RabbitServletContextHandler;

public class WebAppXmlGenerator {

    ServletTestLibrary stl;
    PrintStream out = System.out;

    public WebAppXmlGenerator() {
    }

    public void generateServletDefs() {
        RabbitServletContextHandler rsc = stl.getServletContextHandler();
        ServletHandler servletHandler = rsc.getServletHandler();
        for (ServletHolder holder : servletHandler.getServlets()) {
            holder.getName();
            holder.getDisplayName();
            holder.getClassName();
            holder.getInitParameters();
            holder.getRoleMap();
        }
    }

}

class XmlSTL
        extends ServletTestLibrary {

    @Override
    public ServletHolder addServlet(Class<? extends Servlet> servlet) {
        return null;
    }

}