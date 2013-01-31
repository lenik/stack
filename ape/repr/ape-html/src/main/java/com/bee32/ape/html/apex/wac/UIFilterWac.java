package com.bee32.ape.html.apex.wac;

import org.activiti.explorer.filter.ExplorerFilter;
import org.eclipse.jetty.servlet.FilterHolder;

import com.bee32.plover.servlet.test.AbstractWac;
import com.bee32.plover.servlet.test.ServletTestLibrary;

public class UIFilterWac
        extends AbstractWac {

    @Override
    public int getOrder() {
        return NORMAL_ORDER;
    }

    @Override
    public void configureServlets(ServletTestLibrary stl) {
        // For 5.12-SNAPSHOT or later.
        FilterHolder filterHolder = stl.addFilter(ExplorerFilter.class, "/*");
    }

}
