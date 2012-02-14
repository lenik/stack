package com.bee32.plover.servlet.util;

import com.bee32.plover.servlet.PloverServletModule;
import com.bee32.plover.servlet.test.AbstractWac;
import com.bee32.plover.servlet.test.ServletTestLibrary;

public class ServletBuiltinsWac
        extends AbstractWac {

    @Override
    public int getOrder() {
        return NORMAL_ORDER;
    }

    @Override
    public void configureServlets(ServletTestLibrary stl) {
        stl.addServlet(ClassLoaderDiagServlet.class, PloverServletModule.PREFIX + "/loader/*");
    }

}
