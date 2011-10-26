package com.bee32.plover.servlet.test;

public class ServletTestWac
        extends AbstractWac {

    @Override
    public int getOrder() {
        return NORMAL_ORDER;
    }

    @Override
    public void configureServlets(ServletTestLibrary stl) {
        stl.addFilter(SessionMonitorFilter.class, "/*", 0);
    }

}
