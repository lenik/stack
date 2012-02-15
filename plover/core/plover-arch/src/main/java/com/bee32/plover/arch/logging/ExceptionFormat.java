package com.bee32.plover.arch.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Pattern;

import javax.free.PatternProcessor;

public class ExceptionFormat {

    static final Pattern packagePattern;
    static {
        packagePattern = Pattern.compile("(com\\.bee32\\.(\\w+)\\.\\S+)", Pattern.MULTILINE);
    }

    public static String highlight(Throwable e) {
        StringWriter buffer = new StringWriter(10000);
        e.printStackTrace(new PrintWriter(buffer));
        String stackTrace = buffer.toString();

        String html = stackTrace.replace("<", "&lt;");
        String highlighted = highlightPackageNames(html);
        return highlighted;
    }

    public static String highlightPackageNames(String s) {
        PatternProcessor proc = new PatternProcessor(packagePattern) {

            @Override
            protected void matched(String part) {
                String sub = matcher.group(2);

                String tag = "b";
                String color = null;

                // if (part.contains("$$")) {
                // tag = "i";
                // color = "gray";
                // }

                if ("plover".equals(sub)) {
                    if (!part.contains("plover.orm"))
                        tag = "span";
                    color = "blue";
                }

                else if ("icsf".equals(sub))
                    color = "red";

                else if ("sem".equals(sub))
                    color = "purple";

                String attrs = "";
                if (color != null)
                    attrs = "style='color: " + color + "'";

                String prefix = "<" + tag + " " + attrs + ">";
                String suffix = "</" + tag + ">";

                print(prefix + part + suffix);
            }

        };

        s = proc.process(s);
        return s;
    }

}
