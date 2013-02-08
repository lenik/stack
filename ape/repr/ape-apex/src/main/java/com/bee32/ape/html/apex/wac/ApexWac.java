package com.bee32.ape.html.apex.wac;

import org.activiti.explorer.servlet.ExplorerApplicationServlet;

import com.bee32.ape.html.apex.ApexModule;
import com.bee32.plover.servlet.rabbits.RabbitServletContextHandler;
import com.bee32.plover.servlet.test.AbstractWac;
import com.bee32.plover.servlet.test.ServletTestLibrary;

public class ApexWac
        extends AbstractWac {

    static int ACTIVITI_VERSION = 512;

    @Override
    public int getOrder() {
        return LOW_ORDER;
    }

    @Override
    public void configureContext(ServletTestLibrary stl) {
        RabbitServletContextHandler contextHandler = stl.getServletContextHandler();
        contextHandler.setInitParameter("productionMode", "true");
    }

    @Override
    public void configureServlets(ServletTestLibrary stl) {
        if (ACTIVITI_VERSION < 512) {
            // 5.11
            // Not works: still have problems.
            // stl.addServlet("Vaadin Application Servlet", ExplorerApplicationServlet.class, //
            // "/*", //
            // "/api/*", //
            // "/editor/*", //
            // "/explorer/*", //
            // "/libs/*", //
            // "/VAADIN/*"//
            // );
        } else {
            // 5.12+
            stl.addServlet("Vaadin static files", ExplorerApplicationServlet.class, //
                    "/VAADIN/*");
            stl.addServlet("Explorer application", ExplorerApplicationServlet.class, //
                    ApexModule.PREFIX + "/*");
        }
    }

}
