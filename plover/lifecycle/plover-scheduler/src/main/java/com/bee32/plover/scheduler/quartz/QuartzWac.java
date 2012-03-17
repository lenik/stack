package com.bee32.plover.scheduler.quartz;

import com.bee32.plover.scheduler.stat.QuartzMonitorServlet;
import com.bee32.plover.servlet.test.AbstractWac;
import com.bee32.plover.servlet.test.ServletTestLibrary;

public class QuartzWac
        extends AbstractWac {

    @Override
    public int getOrder() {
        return LOW_ORDER;
    }

    @Override
    public void configureContext(ServletTestLibrary stl) {
        /** @see InitQuartzAndQjcListener */
    }

    @Override
    public void configureServlets(ServletTestLibrary stl) {
        // ServletHolder holder =
        stl.addServlet(QuartzMonitorServlet.class, "/quartz/*");
        // holder.setInitParameter(QuartzConfig.SCHED_SC_KEY_NAME, QuartzConfig.SCHED_SC_KEY);
        stl.getServletContextHandler().addInitParam(QuartzConfig.SCHED_SC_KEY_NAME, QuartzConfig.SCHED_SC_KEY);
    }

}
