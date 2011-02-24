package com.bee32.plover.javascript;

import java.net.URL;
import java.util.Comparator;

public class JavascriptLibraryComparator
        implements Comparator<IJavascriptDependency> {

    @Override
    public int compare(IJavascriptDependency o1, IJavascriptDependency o2) {
        URL url1 = o1.getURL(null); // XXX
        URL url2 = o2.getURL(null);
        String s1 = url1.toString();
        String s2 = url2.toString();
        int order = s1.compareTo(s2);
        return order;
    }

    private static final JavascriptLibraryComparator instance = new JavascriptLibraryComparator();

    public static JavascriptLibraryComparator getInstance() {
        return instance;
    }

}
