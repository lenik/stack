package com.bee32.plover.servlet.test;

import javax.servlet.http.HttpSessionEvent;

public class C_InitSessionListener
        extends C_SessionListener {

    @Override
    protected void sessionCreated(HttpSessionEvent event, ServletTestCase application) {
        application.initSession(event);
    }

    @Override
    protected void sessionDestroyed(HttpSessionEvent event, ServletTestCase application) {
        // HttpSession session = event.getSession();
    }

}
