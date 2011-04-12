package com.bee32.plover.servlet.context;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.restful.RESTfulConfig;

public interface LocationContextConstants {

    LocationContext JAVASCRIPT = new LocationContext("<<JAVASCRIPT>>") {

        private static final long serialVersionUID = 1L;

        @Override
        public String resolve(HttpServletRequest request, String location) {
            return "javascript:" + location;
        }

    };

    LocationContext REQUEST = new LocationContext("<<request>>") {

        private static final long serialVersionUID = 1L;

        @Override
        public String resolve(HttpServletRequest request, String location) {
            if (isAbsolute(location))
                return location;

            // Since the resolve is request-oriented, so let's just echo it..

            return location;
        }
    };

    LocationContext URL = REQUEST;

    LocationContext WEB_APP = new LocationContext("<<servlet-context>>") {

        private static final long serialVersionUID = 1L;

        @Override
        public String resolve(HttpServletRequest request, String location) {
            if (isAbsolute(location))
                return location;

            ServletContext servletContext = request.getSession().getServletContext();

            // context-path == /* or ""
            String contextPath = servletContext.getContextPath();
            return contextPath + "/" + location;
        }

    };

    LocationContext MM = new LocationContext("<<module-manager>>") {

        private static final long serialVersionUID = 1L;

        @Override
        public String resolve(HttpServletRequest request, String location) {
            if (isAbsolute(location))
                return location;

            ServletContext servletContext = request.getSession().getServletContext();

            // context-path == /* or ""
            String contextPath = servletContext.getContextPath();

            // TODO set REST-PREFIX as context attribute.
            return contextPath + RESTfulConfig.preferredPrefix + "/" + location;
        }

    };

    LocationContext STYLE_ROOT = new PredefinedLocationContext("STYLE-ROOT", //
            "http://static.secca-project.com/style/");

    LocationContext STYLE = new PredefinedLocationContext("STYLE", //
            "http://static.secca-project.com/style/default/");

    LocationContext LIB_3RDPARTY = new PredefinedLocationContext("LIB-3RDPARTY", //
            "http://static.secca-project.com/lib2/");

}
