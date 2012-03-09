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
        String host = request.getHeader("X-Forwarded-Host");
        if (host == null) {
            host = request.getHeader("Host");
            if (host == null)
                // host = request.getServerName(); // "localhost"
                host = "localhost"; //
        }
        int colon = host.lastIndexOf(':');
        if (colon == -1)
            host += ":80";
        return host;
    }

}
