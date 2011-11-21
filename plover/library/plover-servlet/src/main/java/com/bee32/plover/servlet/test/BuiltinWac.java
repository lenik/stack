package com.bee32.plover.servlet.test;

import com.bee32.plover.servlet.peripheral.PloverSclMultiplexer;
import com.bee32.plover.servlet.peripheral.PloverSrlMultiplexer;
import com.bee32.plover.servlet.rabbits.Favicon;
import com.bee32.plover.servlet.rabbits.Logo;
import com.bee32.plover.servlet.util.ThreadServletResponseListener;

public class BuiltinWac
        extends AbstractWac {

    @Override
    public int getOrder() {
        return HIGH_ORDER - 1;
    }

    @Override
    public void configureContext(ServletTestLibrary stl) {
        String[] welcomeFiles = stl.welcomeList.toArray(new String[0]);
        stl.getServletContext().setWelcomeFiles(welcomeFiles);

        // Plover SCL dispatcher.
        stl.addEventListener(new PloverSclMultiplexer());
        stl.addEventListener(new PloverSrlMultiplexer());
        /*
         * There is no HttpResponse parameter in servlet-request-event, this is a work-around fix,
         * use filter to get the reponse object.
         */
        stl.addFilter(ThreadServletResponseListener.class, "/*", 0);
    }

    @Override
    public void configureServlets(ServletTestLibrary stl) {
        stl.addFilter(RequestLogger.class, "*", 0);

        stl.addServlet(Favicon.class, "/favicon.ico");

        // The wildcard * is needed, cuz they are class resources, not overlapped resources.
        stl.addServlet(Logo.class, "/logo/*");
        stl.addFilter(Welcome.class, "/", 0);
    }

    @Override
    public void onServerStartup(ServletTestLibrary stl) {
        super.onServerStartup(stl);
    }

    @Override
    public void onServerShutdown(ServletTestLibrary stl) {
        super.onServerShutdown(stl);
    }

}
