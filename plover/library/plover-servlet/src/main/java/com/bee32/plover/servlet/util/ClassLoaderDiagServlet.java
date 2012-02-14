package com.bee32.plover.servlet.util;

import java.net.URL;
import java.net.URLClassLoader;

import javax.free.DisplayName;
import javax.free.Doc;

import com.bee32.plover.html.HtmlTemplate;
import com.bee32.plover.site.SimpleServlet;

public class ClassLoaderDiagServlet
        extends SimpleServlet {

    private static final long serialVersionUID = 1L;

    public ClassLoaderDiagServlet() {
        pages.add("ucp", Classpath.class);
    }

    @DisplayName("Classpath Dump")
    @Doc("Show classpath list")
    public static class Classpath
            extends HtmlTemplate {

        @Override
        protected void _pageContent() {
            ClassLoader scl = ClassLoader.getSystemClassLoader();
            ClassLoader cl = scl;
            while (cl != null) {
                fieldset();
                {
                    legend().text("Loader: " + cl).end();
                    ol();
                    if (cl instanceof URLClassLoader) {
                        URLClassLoader ucl = (URLClassLoader) cl;
                        for (URL url : ucl.getURLs()) {
                            li();
                            String entry = url.toString();
                            if (entry.contains("sem-"))
                                style("color: purple");
                            else if (entry.contains("icsf-"))
                                style("color: red");
                            else if (entry.contains("plover-"))
                                style("color: blue");
                            text(entry);
                            end();
                        }
                    }
                    end();
                    cl = cl.getParent();
                }
                end();
            }
        }

    }

}
