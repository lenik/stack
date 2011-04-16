package com.bee32.plover.servlet.context;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.restful.RESTfulConfig;

public interface LocationContextConstants {

    LocationContext JAVASCRIPT = new JavascriptLocationContext(null);

    LocationContext REQUEST = new RequestLocationContext(null);

    LocationContext URL = REQUEST;

    LocationContext WEB_APP = new ServletLocationContext(null);

    LocationContext MM = new ModuleManagerLocationContext(null);

    LocationContext STYLE_ROOT = new PredefinedLocationContext("STYLE-ROOT", //
            "http://static.secca-project.com/style/");

    LocationContext STYLE = new PredefinedLocationContext("STYLE", //
            "http://static.secca-project.com/style/default/");

    LocationContext ICON = new PredefinedLocationContext("STYLE-ICON", //
            "http://static.secca-project.com/style/default/icons/");

    LocationContext LIB_3RDPARTY = new PredefinedLocationContext("LIB-3RDPARTY", //
            "http://static.secca-project.com/lib2/");

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
    public String resolve(HttpServletRequest request) {
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

    @Override
    public String resolve(HttpServletRequest request) {
        if (isAbsolute(base))
            return base;

        // Since the resolve is request-oriented, so let's just echo it..
        return base;
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
    public String resolve(HttpServletRequest request) {
        if (isAbsolute(base))
            return base;

        ServletContext servletContext = request.getSession().getServletContext();

        // context-path == /* or ""
        String contextPath = servletContext.getContextPath();

        if (base.isEmpty())
            return contextPath;
        else if (base.startsWith("/"))
            // WARNING.
            return contextPath + base;
        else
            return contextPath + "/" + base;
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
    public String resolve(HttpServletRequest request) {
        if (isAbsolute(base))
            return base;

        ServletContext servletContext = request.getSession().getServletContext();

        // context-path == /* or ""
        String contextPath = servletContext.getContextPath();

        // TODO set REST-PREFIX as context attribute.
        return contextPath + RESTfulConfig.preferredPrefix + "/" + base;
    }

}
