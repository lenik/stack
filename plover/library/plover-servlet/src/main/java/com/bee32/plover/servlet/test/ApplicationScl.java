package com.bee32.plover.servlet.test;

import javax.servlet.ServletContextEvent;

import com.bee32.plover.arch.Application;
import com.bee32.plover.servlet.peripheral.AbstractScl;

public class ApplicationScl
        extends AbstractScl {

    int level = 0;

    @Override
    public int getPriority() {
        return -10;
    }

    @Override
    public synchronized void contextInitialized(ServletContextEvent sce) {
        boolean first = level == 0;
        level++;
        if (first)
            Application.initialize();
    }

    @Override
    public synchronized void contextDestroyed(ServletContextEvent sce) {
        level--;
        if (level == 0)
            Application.terminate();
    }

}
