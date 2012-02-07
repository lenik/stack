package com.bee32.plover.sql;

import com.bee32.plover.orm.PloverORMModule;
import com.bee32.plover.servlet.test.AbstractWac;
import com.bee32.plover.servlet.test.ServletTestLibrary;

public class SQLMonitorWac
        extends AbstractWac {

    @Override
    public int getOrder() {
        return NORMAL_ORDER;
    }

    @Override
    public void configureServlets(ServletTestLibrary stl) {
        stl.addServlet(SQLMonitorServlet.class, PloverORMModule.PREFIX + "/sql/*");
    }

}
