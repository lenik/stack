package com.bee32.plover.restful;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class RestfulResponse
        extends HttpServletResponseWrapper
        implements IRestfulResponse {

    private Object target;
    private Throwable exception;

    public RestfulResponse(HttpServletResponse response) {
        super(response);
    }

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

}
