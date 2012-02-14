package com.bee32.plover.orm.wac;

import com.bee32.plover.servlet.test.ServletTestLibrary;
import com.bee32.plover.servlet.util.ToolsetWac;

public class OrmToolsetWac
        extends ToolsetWac {

    @Override
    public int getOrder() {
        return NORMAL_ORDER;
    }

    @Override
    public void configureServlets(ServletTestLibrary stl) {
        super.configureServlets(stl);
    }

}
