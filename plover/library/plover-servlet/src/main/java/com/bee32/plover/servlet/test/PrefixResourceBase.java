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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (hasTrailingSlash ? 1231 : 1237);
        result = prime * result + loader.hashCode();
        result = prime * result + prefix.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        PrefixResourceBase other = (PrefixResourceBase) obj;
        if (hasTrailingSlash != other.hasTrailingSlash)
            return false;

        if (!loader.equals(other.loader))
            return false;

        if (!prefix.equals(other.prefix))
            return false;

        return true;
    }

    @Override
    public String toString() {
        return prefix;
    }

}
