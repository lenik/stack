package com.bee32.zebra.tk.site;

public class QueryString {

    public static String join(String qs1, String qs2) {
        if (qs1 == null)
            return qs2;
        if (qs2 == null)
            return qs1;
        return qs1 + "&" + qs2;
    }

}
