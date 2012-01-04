package com.bee32.plover.servlet.rabbits;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

import com.bee32.plover.servlet.peripheral.DecoratedScl;
import com.bee32.plover.servlet.test.ServletTestCase;
import com.bee32.plover.servlet.test.WiredServletTestCase;

public class RootContextLoaderScl
        extends DecoratedScl {

    public RootContextLoaderScl() {
        super(ContextLoaderListener.class);
    }

    @Override
    public boolean isIncluded(ServletContextEvent sce) {
        ServletTestCase stc = ServletTestCase.getInstanceFromContext(sce.getServletContext());
        return stc instanceof WiredServletTestCase;
    }

    @Override
    public int getPriority() {
        return -10;
    }

}
