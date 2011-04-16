package com.bee32.plover.servlet.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ThreadServletContext {

    static final ThreadLocal<HttpServletRequest> threadLocalRequests = new ThreadLocal<HttpServletRequest>();
    static final ThreadLocal<HttpServletResponse> threadLocalResponses = new ThreadLocal<HttpServletResponse>();

    public static HttpServletRequest getRequest() {
        return threadLocalRequests.get();
    }

    public static HttpServletRequest requireRequest() {
        HttpServletRequest request = getRequest();

        if (request == null)
            throw new IllegalStateException("Not in an http-request scope");

        return request;
    }

    public static void setRequest(HttpServletRequest request) {
        threadLocalRequests.set(request);
    }

    public static HttpServletResponse getResponse() {
        return threadLocalResponses.get();
    }

    public static HttpServletResponse requireResponse() {
        HttpServletResponse response = getResponse();

        if (response == null)
            throw new IllegalStateException("Not in an http-request scope");

        return threadLocalResponses.get();
    }

    public static void setResponse(HttpServletResponse response) {
        threadLocalResponses.set(response);
    }

    public static HttpSession getSession() {
        HttpServletRequest request = getRequest();
        if (request == null)
            return null;
        return request.getSession();
    }

    public static HttpSession requireSession() {
        return requireRequest().getSession();
    }

    public static ServletContext getServletContext() {
        HttpSession session = getSession();
        if (session == null)
            return null;
        return session.getServletContext();
    }

    public static ServletContext requireServletContext() {
        return requireSession().getServletContext();
    }

    public static ServletContext requireApplication() {
        return requireServletContext();
    }

}
