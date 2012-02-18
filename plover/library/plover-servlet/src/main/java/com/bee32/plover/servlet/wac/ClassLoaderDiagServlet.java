package com.bee32.plover.servlet.wac;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;

import javax.free.DisplayName;
import javax.free.Doc;

import com.bee32.plover.html.HtmlTemplate;
import com.bee32.plover.site.SimpleServlet;

public class ClassLoaderDiagServlet
        extends SimpleServlet {

    private static final long serialVersionUID = 1L;

    public ClassLoaderDiagServlet() {
        pages.add("ucp", Classpath.class);
        pages.add("find", Find.class);
    }

    static ClassLoader getLoader(String name) {
        if (name == null)
            name = "scl";
        ClassLoader classLoader;
        switch (name) {
        case "ccl":
            classLoader = Thread.currentThread().getContextClassLoader();
            break;
        case "scl":
        default:
            classLoader = ClassLoader.getSystemClassLoader();
            break;
        }
        return classLoader;
    }

    @DisplayName("Classpath Dump")
    @Doc("Show classpath list")
    public static class Classpath
            extends HtmlTemplate {
        @Override
        protected void _pageContent() {
            String type = getRequest().getParameter("type");
            ClassLoader loader = getLoader(type);
            while (loader != null) {
                fieldset();
                {
                    legend().text("Loader: " + loader).end();
                    ol();
                    if (loader instanceof URLClassLoader) {
                        URLClassLoader ucl = (URLClassLoader) loader;
                        for (URL url : ucl.getURLs()) {
                            li();
                            String entry = url.toString();
                            if (entry.contains("sem-"))
                                style("color: purple");
                            else if (entry.contains("icsf-"))
                                style("color: red");
                            else if (entry.contains("plover-"))
                                style("color: blue");
                            else if (entry.contains("hibernate"))
                                style("font-weight: bold");
                            text(entry);
                            end();
                        }
                    }
                    end();
                    loader = loader.getParent();
                }
                end();
            }
        }
    }

    @DisplayName("Classpath")
    @Doc("Find resource in classpath")
    public static class Find
            extends HtmlTemplate {
        @Override
        protected void _pageContent()
                throws IOException {
            String name = getRequest().getParameter("name");
            if (name == null)
                throw new NullPointerException("name");

            String type = getRequest().getParameter("type");
            ClassLoader loader = getLoader(type);

            h2().text("Found resource " + name + " in " + loader).end();

            Enumeration<URL> resources = loader.getResources(name);
            ol();
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                li();
                text(resource.toString());
                end();
            }
            end();
        }
    }

}
