package com.bee32.plover.util;

public class Pathnames {

    public static String joinPath(String context, String spec) {
        if (spec.startsWith("/"))
            return spec;

        while (true) {
            if (spec.equals(".")) {
                spec = "";
                break;
            }

            if (spec.equals(".."))
                spec = "../.";

            if (spec.startsWith("./")) {
                spec = spec.substring(2);
                continue;
            }

            if (spec.startsWith("../")) {
                spec = spec.substring(3);
                int lastSlash = context.lastIndexOf('/');
                if (lastSlash != -1) {
                    if (context.equals(".")) // ../. => ..
                        context = "..";
                    else if (context.equals("..")) // .. => ../..
                        context += "/..";
                    else if (context.endsWith("/.."))
                        context += "/..";
                    else
                        context = context.substring(0, lastSlash);
                } else
                    context = "..";

                continue;
            }

            break;
        }

        assert spec != null && !spec.startsWith("/");
        int lastSlash = context.lastIndexOf('/');
        if (lastSlash != -1)
            context = context.substring(0, lastSlash);
        else if (context.equals(".."))
            ;
        else
            context = ".";

        String joined = context + '/' + spec;
        return joined;
    }

}
