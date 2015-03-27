package com.bee32.zebra.tk.hbin;

import java.util.StringTokenizer;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlOlTag;

public class PathBreadcrumb
        extends HtmlOlTag {

    public PathBreadcrumb(IHtmlTag parent, String path) {
        super(parent, "ol");
        class_("breadcrumb");

        if (path == null)
            throw new NullPointerException("path");

        StringTokenizer tokens = new StringTokenizer(path, "/");
        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken();
            li().text(token);
        }
    }

}
