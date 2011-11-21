package com.bee32.plover.servlet.util;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.servlet.peripheral.AbstractSrl;

public class ThreadServletRequestListener
        extends AbstractSrl {

    static Logger logger = LoggerFactory.getLogger(ThreadServletRequestListener.class);

    public static final int PRIORITY = -1;

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public void requestInitialized(ServletRequestEvent event) {
        logger.debug("Thread servlet context enter: " + event);

        ServletRequest request = event.getServletRequest();

        if (request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) request;
            ThreadServletContext.setRequest((HttpServletRequest) req);
        }
    }

    @Override
    public void requestDestroyed(ServletRequestEvent event) {
        ThreadServletContext.setRequest(null);
    }

}
