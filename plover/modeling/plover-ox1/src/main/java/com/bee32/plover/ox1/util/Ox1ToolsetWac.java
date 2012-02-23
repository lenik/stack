package com.bee32.plover.ox1.util;

import com.bee32.plover.ox1.PloverOx1Module;
import com.bee32.plover.servlet.test.ServletTestLibrary;
import com.bee32.plover.servlet.util.ToolsetWac;

public class Ox1ToolsetWac
        extends ToolsetWac {

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void configureServlets(ServletTestLibrary stl) {
        stl.addServlet(Ox1SQLServlet.class, PloverOx1Module.PREFIX + "/sql/*");
    }

}
