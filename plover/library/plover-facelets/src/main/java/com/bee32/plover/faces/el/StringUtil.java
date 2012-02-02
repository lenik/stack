package com.bee32.plover.faces.el;

public class StringUtil {

    public static String firstPart(String str, String separator) {
        int index = str.indexOf(separator);
        if (index == -1)
            return str;
        else
            return str.substring(0, index);
    }

    public static String lastPart(String str, String separator) {
        int index = str.lastIndexOf(separator);
        if (index == -1)
            return str;
        else
            return str.substring(index + separator.length());
    }

}
