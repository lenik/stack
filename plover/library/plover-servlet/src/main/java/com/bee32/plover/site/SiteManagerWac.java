package com.bee32.plover.site;

import com.bee32.plover.servlet.PloverServletModule;
import com.bee32.plover.servlet.test.AbstractWac;
import com.bee32.plover.servlet.test.ServletTestLibrary;

public class SiteManagerWac
        extends AbstractWac {

    @Override
    public int getOrder() {
        return NORMAL_ORDER;
    }

    @Override
    public void configureServlets(ServletTestLibrary stl) {
        stl.addServlet(SiteManagerServlet.class, PloverServletModule.PREFIX + "/*");
        // servlet.setInitParameter(param, value)
    }

}
