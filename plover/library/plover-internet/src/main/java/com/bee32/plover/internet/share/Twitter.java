package com.bee32.plover.internet.share;

import com.bee32.plover.internet.ShareSite;

/**
 * 推特
 *
 * <p lang="en">
 * Twitter
 */
public class Twitter
        extends ShareSite {

    static String format = "http://twitter.com/home?status=Currently reading %s";
    static String logo = "http://twitter.com";

    public Twitter() {
        super(format, logo);
    }

}
