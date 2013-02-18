package com.bee32.plover.arch.util;

public class Strs {

    public static boolean isEmpty(String s) {
        if (s == null)
            return true;
        s = s.trim();
        if (s.isEmpty())
            return true;
        return false;
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static boolean isInteger(String s) {
        if (s == null)
            return false;
        s = s.trim();
        if (s.isEmpty())
            return false;

        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}
