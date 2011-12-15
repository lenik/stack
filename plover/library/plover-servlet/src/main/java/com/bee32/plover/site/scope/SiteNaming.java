package com.bee32.plover.site.scope;

import javax.servlet.http.HttpServletRequest;

public class SiteNaming {

    private static String DEFAULT_SITE_NAME = "default";

    /**
     * Specify the default site name.
     *
     * The default site (or fallback) is the site choosed for unmatched address.
     *
     * This name must match the .sif file name in site config dir.
     */
    public static String getDefaultSiteName() {
        return DEFAULT_SITE_NAME;
    }

    /**
     * Specify the default site name.
     *
     * The default site (or fallback) is the site choosed for unmatched address.
     *
     * This name must match the .sif file name in site config dir.
     */
    public static void setDefaultSiteName(String defaultSiteName) {
        if (defaultSiteName == null)
            throw new NullPointerException("defaultSiteName");
        DEFAULT_SITE_NAME = defaultSiteName;
    }

    public static String getSiteAlias(HttpServletRequest request) {
        String host = request.getServerName(); // "localhost"
        // String localName = request.getLocalName(); // "127.0.0.1"
        int port = request.getLocalPort();
        return host + ":" + port;
    }

}
