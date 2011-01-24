package com.bee32.plover.servlet;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;

public abstract class AbstractServletTest {

    protected final MockServletConfig servletConfig;
    protected final MockServletContext application;
    protected final MockHttpSession session;
    protected final MockHttpServletRequest request;
    protected final MockHttpServletResponse response;

    public AbstractServletTest() {
        application = new MockServletContext();
        servletConfig = new MockServletConfig(application);
        session = new MockHttpSession(application);
        request = new MockHttpServletRequest(application);
        response = new MockHttpServletResponse();
    }

}
