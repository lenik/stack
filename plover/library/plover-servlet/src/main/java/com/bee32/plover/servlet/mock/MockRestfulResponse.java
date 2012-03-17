package com.bee32.plover.servlet.mock;

import javax.servlet.http.HttpServletResponse;

import org.springframework.mock.web.MockHttpServletResponse;

import com.bee32.plover.restful.IRESTfulResponse;

public class MockRestfulResponse
        extends MockHttpServletResponse
        implements IRESTfulResponse, HttpServletResponse {

    private Object target;
    private Throwable exception;
    private String method;

    @Override
    public Object getTarget() {
        return target;
    }

    @Override
    public void setTarget(Object target) {
        this.target = target;
    }

    @Override
    public Throwable getException() {
        return exception;
    }

    @Override
    public void setException(Throwable exception) {
        this.exception = exception;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public void setMethod(String method) {
        this.method = method;
    }

}
