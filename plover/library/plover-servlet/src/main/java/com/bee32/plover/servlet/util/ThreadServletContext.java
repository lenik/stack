package com.bee32.plover.servlet.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ThreadServletContext {

    static final ThreadLocal<HttpServletRequest> threadLocalRequests = new ThreadLocal<HttpServletRequest>();
    static final ThreadLocal<HttpServletResponse> threadLocalResponses = new ThreadLocal<HttpServletResponse>();

    public static HttpServletRequest requireRequest() {
        HttpServletRequest request = threadLocalRequests.get();

        if (request == null)
            throw new IllegalStateException("Not in an http-request scope");

        return request;
    }

    public static void setRequest(HttpServletRequest request) {
        threadLocalRequests.set(request);
    }

    public static HttpServletResponse requireResponse() {
        HttpServletResponse response = threadLocalResponses.get();

        if (response == null)
            throw new IllegalStateException("Not in an http-request scope");

        return threadLocalResponses.get();
    }

    public static void setResponse(HttpServletResponse response) {
        threadLocalResponses.set(response);
    }

    public static HttpSession requireSession() {
        return requireRequest().getSession();
    }

    public static ServletContext requireServletContext() {
        return requireSession().getServletContext();
    }

    public static ServletContext requireApplication() {
        return requireServletContext();
    }

}
