package com.bee32.plover.faces.diag;

import java.io.Serializable;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ClassLoaderInfo
        implements Serializable {

    private static final long serialVersionUID = 1L;

    final ClassLoader loader;

    public ClassLoaderInfo(ClassLoader loader) {
        if (loader == null)
            throw new NullPointerException("loader");
        this.loader = loader;
    }

    public Class<?> getType() {
        return loader.getClass();
    }

    public List<URL> getURLs() {
        if (!(loader instanceof URLClassLoader))
            return Collections.emptyList();
        URLClassLoader ucl = (URLClassLoader) loader;
        URL[] urlv = ucl.getURLs();
        return Arrays.asList(urlv);
    }

}
