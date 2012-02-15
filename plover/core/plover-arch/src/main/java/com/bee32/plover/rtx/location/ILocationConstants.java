package com.bee32.plover.rtx.location;

import javax.free.IllegalUsageException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public interface ILocationConstants {

    Location JAVASCRIPT = new JavascriptContextLocation(null);

    Location REQUEST = new RequestContextLocation("Request", null);

    Location URL = new RequestContextLocation("URL", null);

    Location WEB_APP = new ServletContextLocation("/");

    Location LIB_JS = new ServletContextLocation("lib/js/");

    Location STYLE_ROOT = new PredefinedContextLocation("STYLE-ROOT", //
            "https://st1.cdn.bee32.com/style/");

    Location STYLE = new PredefinedContextLocation("STYLE", //
            "https://st1.cdn.bee32.com/style/default/");

    Location ICON = new PredefinedContextLocation("STYLE-ICON", //
            "https://st1.cdn.bee32.com/style/default/icons/");

    Location SYMBOLS = new PredefinedContextLocation("SYMBOLS", //
            "https://st1.cdn.bee32.com/symbols/");

    Location LIB_3RDPARTY = new PredefinedContextLocation("LIB-3RDPARTY", //
            "https://st1.cdn.bee32.com/lib2/");

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
    protected void fillContext(StringBuffer sb, HttpServletRequest request) {
        sb.setLength(0);
        sb.append("javascript:");
        sb.append(base);
    }

    @Override
    public String resolveContextRelative(HttpServletRequest request) {
        if (base == null)
            throw new IllegalUsageException("No script spec for javascript location context");
        return "javascript:" + base;
    }

}

class RequestContextLocation
        extends Location {

    private static final long serialVersionUID = 1L;

    public RequestContextLocation(String name, String base) {
        super(name, base);
    }

    @Override
    protected Location create(String base) {
        return new RequestContextLocation(name, base);
    }

    /**
     * Should not call into this method due to performance reason.
     */
    @Deprecated
    @Override
    protected void fillContext(StringBuffer sb, HttpServletRequest request) {
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
    public String resolveContextRelative(HttpServletRequest request) {
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
    protected void fillContext(StringBuffer sb, HttpServletRequest request) {
        ServletContext servletContext = request.getSession().getServletContext();

        // context-path == /* or ""
        String contextPath = servletContext.getContextPath();
        sb.append(contextPath);
        sb.append("/");
    }

}
