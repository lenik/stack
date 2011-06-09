package com.bee32.plover.servlet.mvc;

import javax.free.Nullables;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class ActionRequest
        extends HttpServletRequestWrapper {

    public static final String METHOD_ATTRIBUTE = "__PLOVER_METHOD";

    public ActionRequest(HttpServletRequest request) {
        super(request);
    }

    public String getMethod() {
        Object method = getAttribute(METHOD_ATTRIBUTE);
        return (String) method;
    }

    public void setMethod(String method) {
        setAttribute(METHOD_ATTRIBUTE, method);
    }

    /**
     * Check if the actual method attribute equals to the given one.
     *
     * @return <code>true</code> If the method attribute is equals to the given one.
     */
    public boolean methodEquals(String method) {
        String actualMethod = getMethod();
        return Nullables.equals(actualMethod, actualMethod);
    }

}
