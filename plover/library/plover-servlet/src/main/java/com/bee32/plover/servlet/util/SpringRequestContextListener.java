package com.bee32.plover.servlet.util;

import javax.servlet.ServletRequestEvent;

import org.springframework.web.context.request.RequestContextListener;

import com.bee32.plover.servlet.peripheral.AbstractSrl;

public class SpringRequestContextListener
        extends AbstractSrl {

    static final int PRIORITY = 0;

    RequestContextListener impl;

    public SpringRequestContextListener() {
        impl = new RequestContextListener();
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        impl.requestInitialized(sre);
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        impl.requestDestroyed(sre);
    }

}
