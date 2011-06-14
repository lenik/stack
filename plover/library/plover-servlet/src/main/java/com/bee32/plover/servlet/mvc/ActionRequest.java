package com.bee32.plover.servlet.mvc;

import javax.free.Nullables;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class ActionRequest
        extends HttpServletRequestWrapper {

    static final String METHOD_ATTRIBUTE = "__PLOVER_METHOD";

    final IActionHandler handler;
    String prefix;
    String pathParameter;
    String actionName;
    String method;

    public ActionRequest(IActionHandler handler, HttpServletRequest request) {
        super(request);
        this.handler = handler;
    }

    public IActionHandler getHandler() {
        return handler;
    }

    /**
     * @return Non-<code>null</code> prefix name.
     */
    public String getPrefix() {
        if (prefix == null)
            throw new IllegalStateException("Prefix wasn't set");
        return prefix;
    }

    /**
     * Initialize the prefix.
     *
     * @param prefix
     *            Non-<code>null</code> prefix name.
     */
    public void setPrefix(String prefix) {
        if (prefix == null)
            throw new NullPointerException("prefix");
        this.prefix = prefix;
    }

    public String getPathParameter() {
        return pathParameter;
    }

    public void setPathParameter(String pathParameter) {
        this.pathParameter = pathParameter;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getMethod() {
        // Object method = getAttribute(METHOD_ATTRIBUTE);
        return (String) method;
    }

    public void setMethod(String method) {
        // setAttribute(METHOD_ATTRIBUTE, method);
        this.method = method;
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

    public String normalizeView(String relativeViewName) {
        return getPrefix() + relativeViewName;
    }

}
