package com.bee32.plover.servlet.mvc;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

/**
 * Extension of model&view with meta-data and vocabulary support.
 *
 * It may be called ViewData in the controller implementations.
 *
 * Example Usage:
 *
 * <pre>
 *  ${meta.typeName}
 *  ${V.CREATE}
 * </pre>
 */
public abstract class ResultView
        extends ModelAndView {

    HttpServletResponse response;

    public ResultView(String viewName, Map<String, ?> model) {
        super(viewName, model);
    }

    public ResultView(String viewName) {
        super(viewName);
    }

    public ResultView(View view, Map<String, ?> model) {
        super(view, model);
    }

    public ResultView(View view) {
        super(view);
    }

    public HttpServletResponse getResponse() {
        if (response == null)
            throw new IllegalStateException("HttpServletResponse isn't set in result view.");
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        if (response == null)
            throw new NullPointerException("response");
        this.response = response;
    }

    public <T> T sendError(int sc, String msg)
            throws IOException {
        response.sendError(sc, msg);
        return null;
    }

    public <T> T sendError(int sc)
            throws IOException {
        response.sendError(sc);
        return null;
    }

    public <T> T sendRedirect(String location)
            throws IOException {
        response.sendRedirect(location);
        return null;
    }

    /**
     * This is the same as
     *
     * <pre>
     * getModel().put(name, value);
     * </pre>
     */
    public void put(String name, Object value) {
        addObject(name, value);
    }

    @Override
    public String toString() {
        return "ToString called";
    }

}
