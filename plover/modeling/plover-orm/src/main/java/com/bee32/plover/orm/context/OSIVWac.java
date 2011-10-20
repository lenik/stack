package com.bee32.plover.orm.context;

import com.bee32.plover.inject.spring.ImportUtil;
import com.bee32.plover.orm.util.WiredDaoTestCase;
import com.bee32.plover.servlet.test.OuterWac;
import com.bee32.plover.servlet.test.ServletTestLibrary;
import com.bee32.plover.servlet.test.WiredServletTestCase;

public class OSIVWac
        extends OuterWac<WiredServletTestCase> {

    @Override
    public int getOrder() {
        // OSIV filter should before dispatch filter.
        return HIGH_ORDER;
    }

    @Override
    public void configureServlets(ServletTestLibrary stl, WiredServletTestCase outer) {
        Class<?> outerType = outer.getClass();
        boolean usingRequired = false;

        for (Class<?> imp : ImportUtil.getImportClasses(outerType)) {
            if (WiredDaoTestCase.class.isAssignableFrom(imp))
                usingRequired = true;
        }

        if (!usingRequired)
            return;

        stl.addFilter(OSIVFilter.class, "/*", 0);
    }

}
