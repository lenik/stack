package com.bee32.plover.servlet.test;

import org.eclipse.jetty.servlet.ServletHolder;

import com.bee32.plover.servlet.rabbits.OverlappedResourceServlet;

public class FallbackWac
        extends AbstractWac {

    @Override
    public int getOrder() {
        return 10000;
    }

    @Override
    public void configureServlets(ServletTestLibrary stl) {
        // Add the fallback-servlet.
        // Otherwise, the filter won't work.

        ServletHolder holder = stl.addServlet(OverlappedResourceServlet.class, "/");
        // See DefaultServlet._welcomeServlets
        holder.setInitParameter("welcomeServlets", "true");
    }

}
