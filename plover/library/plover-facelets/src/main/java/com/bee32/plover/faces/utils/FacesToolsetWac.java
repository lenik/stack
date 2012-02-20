package com.bee32.plover.faces.utils;

import com.bee32.plover.faces.PloverFaceletsModule;
import com.bee32.plover.servlet.test.ServletTestLibrary;
import com.bee32.plover.servlet.util.ToolsetWac;

public class FacesToolsetWac
        extends ToolsetWac {

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void configureServlets(ServletTestLibrary stl) {
        stl.addServlet(ViewStateDumpServlet.class, PloverFaceletsModule.PREFIX + "/state/*");
    }

}
