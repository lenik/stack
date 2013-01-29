package com.bee32.ape.html.apex.wac;

import org.activiti.explorer.servlet.ExplorerApplicationServlet;

import com.bee32.plover.servlet.rabbits.RabbitServletContextHandler;
import com.bee32.plover.servlet.test.AbstractWac;
import com.bee32.plover.servlet.test.ServletTestLibrary;

public class VaadinWac
        extends AbstractWac {

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
        stl.addServlet("Vaadin Application Servlet", //
                ExplorerApplicationServlet.class, "/ui/*", "/VAADIN/*");
    }

}
