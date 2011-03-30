package com.bee32.plover.servlet.util;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

public class ThreadServletContextListener
        implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        ServletRequest request = sre.getServletRequest();

        if (request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) request;
            ThreadServletContext.setRequest((HttpServletRequest) req);
        }
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        ThreadServletContext.setRequest(null);
        ThreadServletContext.setResponse(null);
    }

}
