package com.bee32.plover.restful;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.rtx.location.Location;

/**
 * TODO Inject to ILocationConstants?
 * TODO 1. ILocationConstants.* => LocationRegsitry
 * TODO 2. LocationVerbMap -> LocationRegistry.
 */
public class ModuleManagerLocation
        extends Location {

    private static final long serialVersionUID = 1L;

    public ModuleManagerLocation(String base) {
        super("Module-Manager", base);
    }

    @Override
    protected Location create(String base) {
        return new ModuleManagerLocation(base);
    }

    @Override
    protected void fillContext(StringBuffer sb, HttpServletRequest request) {
        ServletContext servletContext = request.getSession().getServletContext();

        // context-path == /* or ""
        String contextPath = servletContext.getContextPath();

        sb.append(contextPath);
        sb.append(RESTfulConfig.preferredPrefix);
    }

    static final Location MM = new ModuleManagerLocation(null);

}
