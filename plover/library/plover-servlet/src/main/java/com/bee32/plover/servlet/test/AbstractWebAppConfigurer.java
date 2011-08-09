package com.bee32.plover.servlet.test;

public abstract class AbstractWebAppConfigurer
        implements IWebAppConfigurer {

    /**
     * Spring context, etc.
     */
    public static final int HIGH_ORDER = 0;

    /**
     * User order, etc.
     */
    public static final int NORMAL_ORDER = 100;

    /**
     * Quartz, etc.
     */
    public static final int LOW_ORDER = 1000;

    /**
     * OverlappedResources, etc.
     */
    public static final int FALLBACK_ORDER = 100000;

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
