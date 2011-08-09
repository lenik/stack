package com.bee32.plover.orm.context;

import com.bee32.plover.servlet.test.AbstractWebAppConfigurer;
import com.bee32.plover.servlet.test.ServletTestLibrary;

public class OSIVWac
        extends AbstractWebAppConfigurer {

    @Override
    public int getOrder() {
        // OSIV filter should before dispatch filter.
        return HIGH_ORDER;
    }

    @Override
    public void configureServlets(ServletTestLibrary stl) {
        stl.addFilter(OSIVFilter.class, "/*", 0);
    }

}
