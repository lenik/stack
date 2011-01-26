package com.bee32.plover.servlet.test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;

public abstract class ServletTestCase {

    protected final MockServletConfig servletConfig;
    protected final MockServletContext application;
    protected final MockHttpSession session;
    protected final MockHttpServletRequest request;
    protected final MockHttpServletResponse response;

    public ServletTestCase() {
        application = new MockServletContext();
        servletConfig = new MockServletConfig(application);
        session = new MockHttpSession(application);
        request = new MockHttpServletRequest(application);
        response = new MockHttpServletResponse();
    }

}
