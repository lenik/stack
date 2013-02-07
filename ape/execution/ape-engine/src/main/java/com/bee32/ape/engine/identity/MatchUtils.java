package com.bee32.ape.engine.identity;

public class MatchUtils {

    protected static boolean notMatch(String pattern, String str) {
        if (pattern == null)
            return false;
        if (str == null)
            return true;
        return !pattern.equals(str);
    }

    protected static boolean notLike(String pattern, String str) {
        if (pattern == null)
            return false;
        if (str == null)
            return true;
        // pattern = pattern.toLowerCase();
        // str = str.toLowerCase();
        return !str.contains(pattern);
    }

}
