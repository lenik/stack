package com.bee32.zebra.tk.hbin;

import java.util.StringTokenizer;

import net.bodz.bas.html.io.IHtmlOut;

public class PathBreadcrumb {

    public void build(IHtmlOut out, String path) {
        out = out.ol();
        out.class_("breadcrumb");

        if (path == null)
            throw new NullPointerException("path");

        StringTokenizer tokens = new StringTokenizer(path, "/");
        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken();
            out.li().text(token);
        }
    }

}
