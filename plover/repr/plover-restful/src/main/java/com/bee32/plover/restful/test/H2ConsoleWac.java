package com.bee32.plover.restful.test;

import org.eclipse.jetty.servlet.ServletHolder;

import com.bee32.plover.servlet.test.AbstractWac;
import com.bee32.plover.servlet.test.ServletTestLibrary;
import com.bee32.plover.servlet.util.LazyLoadServlet;

public class H2ConsoleWac
        extends AbstractWac {

    @Override
    public int getOrder() {
        return LOW_ORDER;
    }

    @Override
    public void configureServlets(ServletTestLibrary stl) {
        // addEventListener(new DbStarter());

        // context-params?

        ServletHolder servlet = stl.addServlet(LazyLoadServlet.class, "/console/*");
        servlet.setInitParameter("servlet-class", "org.h2.server.web.WebServlet");
        servlet.setInitParameter("db.url", "jdbc:h2:target/testdb");
        // servlet.setInitParameter("webAllowOthers", "");
        // servlet.setInitParameter("trace", "");
        // servlet.loadOnStartup?
    }

}
