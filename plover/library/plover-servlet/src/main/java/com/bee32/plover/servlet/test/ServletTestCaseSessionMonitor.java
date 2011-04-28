package com.bee32.plover.servlet.test;

import javax.servlet.http.HttpSession;

public class ServletTestCaseSessionMonitor
        implements ISessionMonitor {

    @Override
    public void initSession(HttpSession session) {
        ServletTestCase lastInstance = ServletTestCase.getLastInstance();
        if (lastInstance != null) {
            lastInstance.initSession(session);
        }
    }

}
