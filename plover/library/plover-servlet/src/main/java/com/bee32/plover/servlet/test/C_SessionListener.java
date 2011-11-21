package com.bee32.plover.servlet.test;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import com.bee32.plover.servlet.peripheral.AbstractHsl;

/**
 * @see C_Wac
 */
public abstract class C_SessionListener
        extends AbstractHsl {

    @Override
    public final void sessionCreated(HttpSessionEvent se) {
        ServletTestCase application = ServletTestCase.getLastInstance();
        HttpSession session = se.getSession();
        ServletContext servletContext = session.getServletContext();
        sessionCreated(se, application);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        ServletTestCase application = ServletTestCase.getLastInstance();
        HttpSession session = se.getSession();
        ServletContext servletContext = session.getServletContext();
        sessionDestroyed(se, application);
    }

    protected abstract void sessionCreated(HttpSessionEvent event, ServletTestCase application);

    protected abstract void sessionDestroyed(HttpSessionEvent event, ServletTestCase application);

}
