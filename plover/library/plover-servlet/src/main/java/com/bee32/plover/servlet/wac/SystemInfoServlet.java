package com.bee32.plover.servlet.wac;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.free.DisplayName;
import javax.free.Doc;
import javax.free.SystemProperties;

import org.springframework.web.util.HtmlUtils;

import com.bee32.plover.html.HtmlTemplate;
import com.bee32.plover.site.SimpleServlet;

public class SystemInfoServlet
        extends SimpleServlet {

    private static final long serialVersionUID = 1L;

    public SystemInfoServlet() {
        pages.add("props", SystemProps.class);
    }

    @DisplayName("System  Properties")
    @Doc("System properties information")
    public static class SystemProps
            extends HtmlTemplate {

        @Override
        protected void _pageContent() {
            Map<Object, Object> properties = new TreeMap<Object, Object>();
            properties.putAll(System.getProperties());
            table();
            for (Entry<?, ?> entry : properties.entrySet()) {
                String key = String.valueOf(entry.getKey());
                String value = escape(entry.getValue());
                tr();
                th().colspan("2").align("left").text(key).end();
                end();
                tr();
                td().style("width: 10em").end();
                td();
                if (key.contains(".path")) {
                    String[] paths = value.split(SystemProperties.getPathSeparator());
                    ol();
                    for (String path : paths) {
                        li();
                        if (path.contains("sem-"))
                            style("color: purple");
                        else if (path.contains("plover-"))
                            style("color: blue");
                        else if (path.contains("icsf-"))
                            style("color: red");
                        else if (path.contains("hibernate"))
                            style("font-weight: bold");
                        text(path);
                        end();
                    }
                    end();
                } else {
                    text(value);
                }
                end();
                td();
            }
            end();
        }

        static String escape(Object val) {
            String str = String.valueOf(val);
            String html = HtmlUtils.htmlEscape(str);
            return html;
        }

    }

}
