package com.bee32.plover.servlet.test;

import java.net.URL;

public class PrefixResourceBase
        implements IResourceBase {

    private final String prefix;
    private final boolean hasTrailingSlash;

    private ClassLoader loader;

    public PrefixResourceBase(String prefix) {
        if (prefix == null)
            throw new NullPointerException("prefix");

        if (prefix.startsWith("/"))
            prefix = prefix.substring(1);

        this.prefix = prefix;
        this.hasTrailingSlash = prefix.endsWith("/");

        loader = getClass().getClassLoader();
    }

    @Override
    public URL getResource(String name) {
        if (name.startsWith("/") && hasTrailingSlash)
            name = name.substring(1);

        String path = prefix + name;

        return loader.getResource(path);
    }

    @Override
    public String toString() {
        return prefix;
    }

}
