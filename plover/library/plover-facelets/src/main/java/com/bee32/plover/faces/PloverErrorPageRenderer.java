package com.bee32.plover.faces;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.regex.Pattern;

import javax.free.PatternProcessor;

import org.apache.myfaces.renderkit.DefaultErrorPageRenderer;

public class PloverErrorPageRenderer
        extends DefaultErrorPageRenderer {

    static final Pattern pattern;
    static {
        pattern = Pattern.compile("(com\\.bee32\\.(\\w+)\\.\\S+)", Pattern.MULTILINE);
    }

    @Override
    public void writeException(Writer writer, Throwable e)
            throws IOException {
        StringWriter buffer = new StringWriter(10000);
        e.printStackTrace(new PrintWriter(buffer));

        String s = buffer.toString();
        s = s.replaceAll("<", "&lt;");

        String html = highlight(s);

        writer.write(html);
    }

    public static String highlight(String s) {
        PatternProcessor proc = new PatternProcessor(pattern) {

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
