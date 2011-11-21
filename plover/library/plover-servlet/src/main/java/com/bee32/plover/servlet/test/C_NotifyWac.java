package com.bee32.plover.servlet.test;

import com.bee32.plover.servlet.test.ServletTestCase.LocalSTL;

public class C_NotifyWac
        extends C_Wac<ServletTestCase> {

    @Override
    public int getOrder() {
        return NORMAL_ORDER - 1;
    }

    ServletTestCase getOuter(ServletTestLibrary stl) {
        if (!(stl instanceof LocalSTL))
            return null;
        LocalSTL localStl = (LocalSTL) stl;
        return localStl.getOuter();
    }

    @Override
    protected void configureServer(ServletTestLibrary stl, ServletTestCase outer) {
        // outer.configureServer();
    }

    @Override
    protected void configureContext(ServletTestLibrary stl, ServletTestCase outer) {
        outer.configureContext();
    }

    @Override
    protected void configureServlets(ServletTestLibrary stl, ServletTestCase outer) {
        outer.configureServlets();
    }

    @Override
    protected void onServerStartup(ServletTestLibrary stl, ServletTestCase outer) {
        outer.onServerStartup();
    }

    @Override
    protected void onServerShutdown(ServletTestLibrary stl, ServletTestCase outer) {
        outer.onServerShutdown();
    }

}
