package com.bee32.plover.site;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

public class RequestEntry
        implements Serializable {

    private static final long serialVersionUID = 1L;

    String requestURI;

    public RequestEntry(HttpServletRequest req) {
        requestURI = req.getRequestURI();
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

}
