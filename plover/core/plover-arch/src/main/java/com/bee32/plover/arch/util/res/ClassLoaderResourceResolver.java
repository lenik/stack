package com.bee32.plover.arch.util.res;

import java.net.MalformedURLException;
import java.net.URL;

public class ClassLoaderResourceResolver
        implements IResourceResolver {

    private final ClassLoader loader;

    public ClassLoaderResourceResolver(ClassLoader loader) {
        if (loader == null)
            throw new NullPointerException("loader");
        this.loader = loader;
    }

    @Override
    public URL resolve(String spec)
            throws MalformedURLException {
        if (spec == null)
            throw new NullPointerException("spec");

        return loader.getResource(spec);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((loader == null) ? 0 : loader.hashCode());
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

        ClassLoaderResourceResolver other = (ClassLoaderResourceResolver) obj;

        if (!loader.equals(other))
            return false;

        return true;
    }

    @Override
    public String toString() {
        return loader.toString();
    }

}
