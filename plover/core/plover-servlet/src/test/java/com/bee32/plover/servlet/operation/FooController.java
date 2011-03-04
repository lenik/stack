package com.bee32.plover.servlet.operation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A controller like class exports service operations
 */
public class FooController {

    private HttpServletRequest lastRequest;
    private HttpServletResponse lastResponse;

    public String hello(HttpServletRequest req, HttpServletResponse resp) {
        return "world";
    }

    public void testmock(HttpServletRequest req, HttpServletResponse resp) {
        this.lastRequest = req;
        this.lastResponse = resp;
    }

    public HttpServletRequest getLastRequest() {
        return lastRequest;
    }

    public HttpServletResponse getLastResponse() {
        return lastResponse;
    }

}
