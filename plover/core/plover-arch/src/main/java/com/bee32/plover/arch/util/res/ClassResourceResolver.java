package com.bee32.plover.arch.util.res;

import java.net.MalformedURLException;
import java.net.URL;

public class ClassResourceResolver
        implements IResourceResolver {

    private final Class<?> baseClass;

    public ClassResourceResolver(Class<?> baseClass) {
        if (baseClass == null)
            throw new NullPointerException("baseClass");
        this.baseClass = baseClass;
    }

    @Override
    public URL resolve(String spec)
            throws MalformedURLException {
        return baseClass.getResource(spec);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((baseClass == null) ? 0 : baseClass.hashCode());
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

        ClassResourceResolver other = (ClassResourceResolver) obj;

        if (!baseClass.equals(other.baseClass))
            return false;

        return true;
    }

    @Override
    public String toString() {
        return baseClass.getSimpleName() + "-local-resolver";
    }

}
