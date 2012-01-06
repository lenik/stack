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

    static synchronized List<IServletContextListener> getServletContextListeners() {
        if (sclv == null) {
            sclv = new ArrayList<IServletContextListener>();
            for (IServletContextListener scl : ServiceLoader.load(IServletContextListener.class)) {
                sclv.add(scl);
            }
            Collections.sort(sclv, PriorityComparator.INSTANCE);
        }
        return sclv;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        for (IServletContextListener listener : getServletContextListeners()) {
            if (!listener.isIncluded(sce))
                continue;
            logger.debug("SCL-init: " + listener.getClass().getName());
            try {
                listener.contextInitialized(sce);
            } catch (Exception e) {
                logger.error("SCL-Init error: " + listener.getClass().getName(), e);
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        List<IServletContextListener> listeners = getServletContextListeners();
        for (int i = listeners.size() - 1; i >= 0; i--) {
            IServletContextListener listener = listeners.get(i);
            if (!listener.isIncluded(sce))
                continue;
            logger.debug("SCL-Terminate: " + listener.getClass().getName());
            try {
                listener.contextDestroyed(sce);
            } catch (Exception e) {
                logger.error("SCL-Terminate error: " + listener.getClass().getName(), e);
            }
        }
    }

}
