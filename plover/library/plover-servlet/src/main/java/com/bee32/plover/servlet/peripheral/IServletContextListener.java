package com.bee32.plover.servlet.peripheral;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.bee32.plover.arch.util.IPriority;

public interface IServletContextListener
        extends ServletContextListener, IPriority {

    boolean isIncluded(ServletContextEvent sce);

}
