package com.bee32.plover.site;

import com.bee32.plover.servlet.test.AbstractWac;
import com.bee32.plover.servlet.test.ServletTestLibrary;
import com.bee32.plover.site.scope.SiteStartStopListener;

public class SiteWac
        extends AbstractWac {

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void configureContext(ServletTestLibrary stl) {
        stl.addEventListener(new SiteStartStopListener());
    }

}
