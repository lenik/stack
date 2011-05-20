package com.bee32.plover.servlet.mvc;

import javax.servlet.http.HttpServletRequest;

public abstract class RequestHandler
        implements IRequestHandler {

    // protected static final String ACTION_NAME = "action.name";
    // protected static final String ACTION_PARAM = "action.param";

    protected static final int PRIORITY_HIGH = 0;
    protected static final int PRIORITY_DEFAULT = 10;
    protected static final int PRIORITY_LOW = 20;

    protected final String prefix;

    private RequestHandler(String prefix) {
        if (prefix == null)
            throw new NullPointerException("prefix");
        this.prefix = prefix;
    }

    @Override
    public int getPriority() {
        return PRIORITY_DEFAULT;
    }

    @Override
    public boolean accept(HttpServletRequest request) {
        String uri = request.getRequestURI();

        return false;
    }

    protected String parseActionPath(String url) {
        return null;
    }

}
