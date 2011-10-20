package com.bee32.plover.scheduler.quartz;

import com.bee32.plover.scheduler.stat.QuartzMonitorServlet;
import com.bee32.plover.servlet.test.OuterWac;
import com.bee32.plover.servlet.test.ServletTestLibrary;
import com.bee32.plover.servlet.test.WiredServletTestCase;

public class QuartzWac
        extends OuterWac<WiredServletTestCase> {

    @Override
    public int getOrder() {
        return LOW_ORDER;
    }

    @Override
    public void configureContext(ServletTestLibrary stl, WiredServletTestCase outer) {
        /** @see InitQuartzAndQjcListener */
    }

    @Override
    public void configureServlets(ServletTestLibrary stl, WiredServletTestCase outer) {
        // ServletHolder holder =
        stl.addServlet(QuartzMonitorServlet.class, "/quartz/*");
        // holder.setInitParameter(QuartzConfig.SCHED_SC_KEY_NAME, QuartzConfig.SCHED_SC_KEY);
        stl.getServletContext().addInitParam(QuartzConfig.SCHED_SC_KEY_NAME, QuartzConfig.SCHED_SC_KEY);
    }

}
