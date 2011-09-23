package com.bee32.plover.site;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class SiteNaming {

    /**
     * PORT.
     */
    static final Pattern normHostPattern;
    static {
        // PORT.NODE.CLASS.bee32.com
        normHostPattern = Pattern.compile("^(\\d+)\\.(\\d+)\\.(\\d+)\\..*");
    }

    public static String getSiteName(HttpServletRequest request) {
        String host = request.getRemoteHost();

        Matcher normHostMatcher = normHostPattern.matcher(host);
        if (normHostMatcher.matches()) {
            int port = Integer.parseInt(normHostMatcher.group(1));
            int node = Integer.parseInt(normHostMatcher.group(2));
            int cls = Integer.parseInt(normHostMatcher.group(3));
            String fileName = String.format("%d.%d.%d", cls, node, port);
        }

        return null;
    }

}
