package com.bee32.plover.scheduler.quartz;

import com.bee32.plover.servlet.test.AbstractWebAppConfigurer;
import com.bee32.plover.servlet.test.ServletTestLibrary;

public class QuartzWac
        extends AbstractWebAppConfigurer {

    @Override
    public int getOrder() {
        return LOW_ORDER;
    }

    @Override
    public void configureContext(ServletTestLibrary stl) {
        // stl.addServlet(QuartzInitializerServlet.class);
        stl.addEventListener(new QuartzInitializerListener());
    }

}
