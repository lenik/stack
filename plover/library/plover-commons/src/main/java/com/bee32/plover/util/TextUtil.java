package com.bee32.plover.util;

import java.util.regex.Pattern;

public class TextUtil {

    static final Pattern NORM_SPACE;
    static {
        NORM_SPACE = Pattern.compile("\\s+", Pattern.MULTILINE);
    }

    public static String normalizeSpace(String s) {
        return normalizeSpace(s, false);
    }

    public static String normalizeSpace(String s, boolean nullReduce) {
        if (s == null)
            return null;
        s = s.trim();
        if (s.isEmpty()) {
            if (nullReduce)
                s = null;
        } else {
            s = NORM_SPACE.matcher(s).replaceAll(" ");
        }
        return s;
    }

}
