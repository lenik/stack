package com.bee32.plover.servlet.wac;

import com.bee32.plover.servlet.PloverServletModule;
import com.bee32.plover.servlet.central.PloverServletCentral;
import com.bee32.plover.servlet.test.ServletTestLibrary;
import com.bee32.plover.servlet.util.ToolsetWac;

public class SystemToolsetWac
        extends ToolsetWac {

    @Override
    public int getOrder() {
        return NORMAL_ORDER;
    }

    @Override
    public void configureServlets(ServletTestLibrary stl) {
        stl.addServlet(ClassLoaderDiagServlet.class, PloverServletModule.PREFIX + "/loader/*");
        stl.addServlet(SystemInfoServlet.class, PloverServletModule.PREFIX + "/sys/*");
        stl.addServlet(WacToolsServlet.class, PloverServletModule.PREFIX + "/wac/*");
        stl.addServlet(PloverServletCentral.class, "/servlet/*");
    }

}
