package com.bee32.plover.javascript;

import java.net.URL;
import java.util.Comparator;

public class JavascriptLibraryComparator
        implements Comparator<IJavascriptLibrary> {

    @Override
    public int compare(IJavascriptLibrary o1, IJavascriptLibrary o2) {
        URL url1 = o1.getLibraryURL();
        URL url2 = o2.getLibraryURL();
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
