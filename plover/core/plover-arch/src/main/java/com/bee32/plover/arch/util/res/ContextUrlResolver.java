package com.bee32.plover.arch.util.res;

import java.net.MalformedURLException;
import java.net.URL;

import javax.free.Nullables;

public class ContextUrlResolver
        implements IResourceResolver {

    private final URL context;

    public ContextUrlResolver(URL context) {
        if (context == null)
            throw new NullPointerException("context");
        this.context = context;
    }

    @Override
    public URL resolve(String spec)
            throws MalformedURLException {
        return new URL(context, spec);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((context == null) ? 0 : context.hashCode());
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

        ContextUrlResolver other = (ContextUrlResolver) obj;

        if (!Nullables.equals(context, other.context))
            return false;

        return true;
    }

    @Override
    public String toString() {
        return context.toString();
    }

}
