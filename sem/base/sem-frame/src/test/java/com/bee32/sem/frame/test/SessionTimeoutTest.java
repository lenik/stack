package com.bee32.sem.frame.test;

import java.io.IOException;

import org.eclipse.jetty.server.SessionManager;
import org.eclipse.jetty.server.session.SessionHandler;

import com.bee32.plover.faces.test.FaceletsTestCase;
import com.bee32.plover.orm.PloverORMUnit;
import com.bee32.plover.orm.unit.Using;

@Using(PloverORMUnit.class)
public class SessionTimeoutTest
        extends FaceletsTestCase {

    @Override
    protected void configureContext() {
        SessionHandler sessionHandler = stl.getServletContextHandler().getSessionHandler();
        SessionManager sessionManager = sessionHandler.getSessionManager();
        int sessionTimeout = sessionManager.getMaxInactiveInterval();
        sessionTimeout = 1;
        sessionManager.setMaxInactiveInterval(sessionTimeout);
    }

    @Override
    protected int getRefreshPeriod() {
        return 1;
    }

    public static void main(String[] args)
            throws IOException {
        new SessionTimeoutTest().browseAndWait("test/session-timeout.jsf");
    }

}
