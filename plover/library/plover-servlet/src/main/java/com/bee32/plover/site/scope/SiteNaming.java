package com.bee32.plover.site.scope;

import javax.servlet.http.HttpServletRequest;

public class SiteNaming {

    public static final String DEFAULT = "default";

    public static String getSiteAlias(HttpServletRequest request) {
        String host = request.getServerName(); // "localhost"
        // String localName = request.getLocalName(); // "127.0.0.1"
        int port = request.getLocalPort();
        return host + ":" + port;
    }

}
