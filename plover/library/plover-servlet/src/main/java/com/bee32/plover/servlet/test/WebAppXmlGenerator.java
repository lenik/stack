package com.bee32.plover.servlet.test;

import java.io.PrintStream;

import org.mortbay.jetty.servlet.ServletHandler;
import org.mortbay.jetty.servlet.ServletHolder;

import com.bee32.plover.servlet.rabbits.RabbitServletContext;

public class WebAppXmlGenerator {

    ServletTestLibrary stl;
    PrintStream out = System.out;

    public WebAppXmlGenerator() {
    }

    public void generateServletDefs() {
        RabbitServletContext rsc = stl.getServletContext();
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
    public ServletHolder addServlet(Class<?> servlet) {
        return null;
    }

}