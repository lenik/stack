package com.bee32.plover.internet.share;

import com.bee32.plover.internet.ShareSite;

public class Twitter
        extends ShareSite {

    static String format = "http://twitter.com/home?status=Currently reading %s";
    static String logo = "http://twitter.com";

    public Twitter() {
        super(format, logo);
    }

}
