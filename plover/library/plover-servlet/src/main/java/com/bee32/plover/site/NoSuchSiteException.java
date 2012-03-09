package com.bee32.plover.site;

public class NoSuchSiteException
        extends SiteException {

    private static final long serialVersionUID = 1L;

    public NoSuchSiteException(String siteName) {
        super(siteName, "");
    }

    public NoSuchSiteException(String siteName, String message, Throwable cause) {
        super(siteName, message, cause);
    }

}
