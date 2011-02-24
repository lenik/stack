package com.bee32.plover.javascript;

import java.net.URL;

public abstract class JavascriptDependency
        extends JavascriptDependant
        implements IJavascriptDependency {

    protected URL getClasspathURL(Class<?> clazz, String spec) {
        URL url = clazz.getResource(spec);
        return url;
    }

}
