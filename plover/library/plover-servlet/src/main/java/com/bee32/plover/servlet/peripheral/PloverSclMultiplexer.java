package com.bee32.plover.servlet.peripheral;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.util.PriorityComparator;

public class PloverSclMultiplexer
        implements ServletContextListener {

    static Logger logger = LoggerFactory.getLogger(PloverSclMultiplexer.class);

    static List<IServletContextListener> sclv;
    static {
        sclv = new ArrayList<IServletContextListener>();
        for (IServletContextListener scl : ServiceLoader.load(IServletContextListener.class)) {
            sclv.add(scl);
        }
        Collections.sort(sclv, PriorityComparator.INSTANCE);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        for (IServletContextListener scl : sclv) {
            if (!scl.isIncluded(sce))
                continue;
            logger.debug("SCL-init: " + scl.getClass().getName());
            try {
                scl.contextInitialized(sce);
            } catch (Exception e) {
                logger.error("SCL-Init error: " + scl.getClass().getName(), e);
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        for (int i = sclv.size() - 1; i >= 0; i--) {
            IServletContextListener scl = sclv.get(i);
            if (!scl.isIncluded(sce))
                continue;
            logger.debug("SCL-Terminate: " + scl.getClass().getName());
            try {
                scl.contextDestroyed(sce);
            } catch (Exception e) {
                logger.error("SCL-Terminate error: " + scl.getClass().getName(), e);
            }
        }
    }

}
