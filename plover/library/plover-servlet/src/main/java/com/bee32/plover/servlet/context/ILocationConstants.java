package com.bee32.plover.servlet.context;

import javax.free.IllegalUsageException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.restful.RESTfulConfig;

public interface ILocationConstants {

    Location JAVASCRIPT = new JavascriptContextLocation(null);

    Location REQUEST = new RequestContextLocation(null);

    Location URL = REQUEST;

    Location WEB_APP = new ServletContextLocation(null);

    Location LIB_JS = new ServletContextLocation("lib/js/");

    Location MM = new ModuleManagerContextLocation(null);

    Location STYLE_ROOT = new PredefinedContextLocation("STYLE-ROOT", //
            "http://static.secca-project.com/style/");

    Location STYLE = new PredefinedContextLocation("STYLE", //
            "http://static.secca-project.com/style/default/");

    Location ICON = new PredefinedContextLocation("STYLE-ICON", //
            "http://static.secca-project.com/style/default/icons/");

    Location LIB_3RDPARTY = new PredefinedContextLocation("LIB-3RDPARTY", //
            "http://static.secca-project.com/lib2/");

}

class JavascriptContextLocation
        extends Location {

    private static final long serialVersionUID = 1L;

    public JavascriptContextLocation(String script) {
        super("<JavaScript>", script);
    }

    @Override
    protected Location create(String script) {
        return new JavascriptContextLocation(script);
    }

    @Override
    protected void getContext(StringBuffer sb, HttpServletRequest request) {
        throw new IllegalUsageException("Do not convert javascript-location to URL or URI.");
    }

    @Override
    public String resolveAbsolute(HttpServletRequest request) {
        if (base == null)
            throw new IllegalUsageException("No script spec for javascript location context");
        return "javascript:" + base;
    }

}

class RequestContextLocation
        extends Location {

    private static final long serialVersionUID = 1L;

    public RequestContextLocation(String base) {
        super("Request", base);
    }

    @Override
    protected Location create(String base) {
        return new RequestContextLocation(base);
    }

    /**
     * Should not call into this method due to performance reason.
     */
    @Deprecated
    @Override
    protected void getContext(StringBuffer sb, HttpServletRequest request) {
        StringBuffer contextURL = getContextURL(request);
        String context;

        // ...://HOST:PORT/...
        int p = contextURL.indexOf("://");
        if (p != -1)
            context = contextURL.substring(p + 3);
        else
            context = contextURL.toString();

        // HOST:PORT/...
        p = context.indexOf("/");
        if (p != -1)
            context = context.substring(p + 1);
        else
            context = "";

        sb.append(context);
    }

    @Override
    protected StringBuffer getContextURL(HttpServletRequest request) {
        return request.getRequestURL();
    }

    @Override
    public String resolve(HttpServletRequest request) {
        // Since the resolve is request-oriented, so let's just echo it..
        return base;
    }

    @Override
    public String resolveAbsolute(HttpServletRequest request) {
        String context = request.getRequestURI();
        StringBuffer buffer = new StringBuffer(context);
        return join(buffer, base);
    }

}

class ServletContextLocation
        extends Location {

    private static final long serialVersionUID = 1L;

    public ServletContextLocation(String base) {
        super("Servlet-Context", base);
    }

    @Override
    protected Location create(String base) {
        return new ServletContextLocation(base);
    }

    @Override
    protected void getContext(StringBuffer sb, HttpServletRequest request) {
        ServletContext servletContext = request.getSession().getServletContext();

        // context-path == /* or ""
        String contextPath = servletContext.getContextPath();
        sb.append(contextPath);
        sb.append("/");
    }

}

class ModuleManagerContextLocation
        extends Location {

    private static final long serialVersionUID = 1L;

    public ModuleManagerContextLocation(String base) {
        super("Module-Manager", base);
    }

    @Override
    protected Location create(String base) {
        return new ModuleManagerContextLocation(base);
    }

    @Override
    protected void getContext(StringBuffer sb, HttpServletRequest request) {
        ServletContext servletContext = request.getSession().getServletContext();

        // context-path == /* or ""
        String contextPath = servletContext.getContextPath();

        sb.append(contextPath);
        sb.append(RESTfulConfig.preferredPrefix);
    }

}
