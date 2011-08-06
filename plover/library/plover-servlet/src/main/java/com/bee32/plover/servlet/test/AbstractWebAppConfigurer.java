package com.bee32.plover.servlet.test;

public abstract class AbstractWebAppConfigurer
        implements IWebAppConfigurer {

    protected static final int PLOVER_ORDER_BASE = 0;
    protected static final int USER_ORDER_BASE = 100;

    @Override
    public void configureServer(ServletTestLibrary stl) {
    }

    @Override
    public void configureContext(ServletTestLibrary stl) {
    }

    @Override
    public void configureServlets(ServletTestLibrary stl) {
    }

    @Override
    public void onServerStartup(ServletTestLibrary stl) {
    }

    @Override
    public void onServerShutdown(ServletTestLibrary stl) {
    }

}
