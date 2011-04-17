package com.bee32.plover.servlet.context;

import javax.free.IllegalUsageException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.restful.RESTfulConfig;

public interface LocationContextConstants {

    LocationContext JAVASCRIPT = new JavascriptLocationContext(null);

    LocationContext REQUEST = new RequestLocationContext(null);

    LocationContext URL = REQUEST;

    LocationContext WEB_APP = new ServletLocationContext(null);

    LocationContext LIB_JS = new ServletLocationContext("lib/js");

    LocationContext MM = new ModuleManagerLocationContext(null);

    LocationContext STYLE_ROOT = new PredefinedLocationContext("STYLE-ROOT", //
            "http://static.secca-project.com/style");

    LocationContext STYLE = new PredefinedLocationContext("STYLE", //
            "http://static.secca-project.com/style/default");

    LocationContext ICON = new PredefinedLocationContext("STYLE-ICON", //
            "http://static.secca-project.com/style/default/icons");

    LocationContext LIB_3RDPARTY = new PredefinedLocationContext("LIB-3RDPARTY", //
            "http://static.secca-project.com/lib2");

}

class JavascriptLocationContext
        extends LocationContext {

    private static final long serialVersionUID = 1L;

    public JavascriptLocationContext(String script) {
        super("<<JAVASCRIPT>>", script);
    }

    @Override
    protected LocationContext create(String script) {
        return new JavascriptLocationContext(script);
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

class RequestLocationContext
        extends LocationContext {

    private static final long serialVersionUID = 1L;

    public RequestLocationContext(String base) {
        super("<<request>>", base);
    }

    @Override
    protected LocationContext create(String base) {
        return new RequestLocationContext(base);
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
        return join(context, base);
    }

}

class ServletLocationContext
        extends LocationContext {

    private static final long serialVersionUID = 1L;

    public ServletLocationContext(String base) {
        super("<<servlet-context>>", base);
    }

    @Override
    protected LocationContext create(String base) {
        return new ServletLocationContext(base);
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

class ModuleManagerLocationContext
        extends LocationContext {

    private static final long serialVersionUID = 1L;

    public ModuleManagerLocationContext(String base) {
        super("<<module-manager>>", base);
    }

    @Override
    protected LocationContext create(String base) {
        return new ModuleManagerLocationContext(base);
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
