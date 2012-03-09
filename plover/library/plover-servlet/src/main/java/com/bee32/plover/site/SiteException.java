package com.bee32.plover.site;

public class SiteException
        extends RuntimeException {

    private static final long serialVersionUID = 1L;

    String siteName;

    public SiteException(String siteName, String message) {
        super(message);
        this.siteName = siteName;
    }

    public SiteException(String siteName, String message, Throwable cause) {
        super(message, cause);
        this.siteName = siteName;
    }

}
