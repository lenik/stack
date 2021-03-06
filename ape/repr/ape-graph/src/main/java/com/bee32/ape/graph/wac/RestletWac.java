package com.bee32.ape.graph.wac;

import org.activiti.editor.rest.application.ActivitiRestApplication;
import org.eclipse.jetty.servlet.ServletHolder;
import org.restlet.ext.servlet.ServerServlet;

import com.bee32.ape.graph.ApeGraphModule;
import com.bee32.plover.servlet.test.AbstractWac;
import com.bee32.plover.servlet.test.ServletTestLibrary;

public class RestletWac
        extends AbstractWac {

    @Override
    public int getOrder() {
        return NORMAL_ORDER;
    }

    @Override
    public void configureServlets(ServletTestLibrary stl) {
        ServletHolder servletHolder = stl.addServlet("RestletServlet", //
                ServerServlet.class, ApeGraphModule.PREFIX + "/*");
        servletHolder.setInitParameter("org.restlet.application", //
                ActivitiRestApplication.class.getName());
    }

}
