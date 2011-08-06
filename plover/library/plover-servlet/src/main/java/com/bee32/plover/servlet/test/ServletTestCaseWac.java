package com.bee32.plover.servlet.test;

import com.bee32.plover.servlet.test.ServletTestCase.LocalSTL;

public class ServletTestCaseWac
        extends AbstractWebAppConfigurer {

    @Override
    public int getOrder() {
        return USER_ORDER_BASE - 1;
    }

    ServletTestCase getOuter(ServletTestLibrary stl) {
        if (!(stl instanceof LocalSTL))
            return null;
        LocalSTL localStl = (LocalSTL) stl;
        return localStl.getOuter();
    }

    @Override
    public void configureServer(ServletTestLibrary stl) {
        ServletTestCase stc = getOuter(stl);
        if (stc != null)
            ; // stc.configureServer();
    }

    @Override
    public void configureContext(ServletTestLibrary stl) {
        ServletTestCase stc = getOuter(stl);
        if (stc != null)
            stc.configureContext();
    }

    @Override
    public void configureServlets(ServletTestLibrary stl) {
        ServletTestCase stc = getOuter(stl);
        if (stc != null)
            stc.configureServlets();
    }

    @Override
    public void onServerStartup(ServletTestLibrary stl) {
        ServletTestCase stc = getOuter(stl);
        if (stc != null)
            stc.onServerStartup();
    }

    @Override
    public void onServerShutdown(ServletTestLibrary stl) {
        ServletTestCase stc = getOuter(stl);
        if (stc != null)
            stc.onServerShutdown();
    }

}
