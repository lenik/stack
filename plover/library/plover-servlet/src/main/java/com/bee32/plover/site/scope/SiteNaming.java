package com.bee32.plover.site.scope;

import javax.servlet.http.HttpServletRequest;

public class SiteNaming {

    public static String getSiteAlias(HttpServletRequest request) {
        String host = request.getLocalName();
        int port = request.getLocalPort();
        return host + ":" + port;
    }

}
