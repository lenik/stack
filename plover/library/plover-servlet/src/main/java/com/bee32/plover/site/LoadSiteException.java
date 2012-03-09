package com.bee32.plover.site;

public class LoadSiteException
        extends SiteException {

    private static final long serialVersionUID = 1L;

    public LoadSiteException(String siteName, String message, Throwable cause) {
        super(siteName, message, cause);
    }

    public LoadSiteException(String siteName, String message) {
        super(siteName, message);
    }

}
