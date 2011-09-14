package com.bee32.sem.misc;

import java.util.regex.Pattern;

public class TextUtil {

    static final Pattern NORM_SPACE;
    static {
        NORM_SPACE = Pattern.compile("\\s+", Pattern.MULTILINE);
    }

    public static String normalizeSpace(String s) {
        if (s == null)
            return null;
        s = s.trim();
        s = NORM_SPACE.matcher(s).replaceAll(" ");
        return s;
    }

}
