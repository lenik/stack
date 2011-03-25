package com.bee32.plover.servlet.context;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.free.IllegalUsageException;
import javax.servlet.http.HttpServletRequest;

public abstract class LocationContext
        implements ILocationContext, Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;

    public LocationContext(String name) {
        this.name = name;
    }

    @Override
    public ContextLocation join(String location) {
        if (location == null)
            throw new NullPointerException("location");

        return new ContextLocation(this, location);
    }

    /**
     * Get the base URL of this context.
     *
     * @return Non-<code>null</code> context URL, or <code>null</code> if this context can't be
     *         converted to a URL.
     */
    protected URL getContextUrl(HttpServletRequest request) {
        throw new IllegalUsageException("Can't convert " + this + " context to URL.");
    }

    @Override
    public URL resolveUrl(HttpServletRequest request, String location)
            throws MalformedURLException {
        URL contextUrl = getContextUrl(request);
        return new URL(contextUrl, location);
    }

    @Override
    public final URI resolveUri(HttpServletRequest request, String location)
            throws URISyntaxException {
        try {
            URL url = resolveUrl(request, location);
            return url.toURI();
        } catch (MalformedURLException e) {
            throw new URISyntaxException(location, e.getMessage());
        }
    }

    protected boolean isAbsolute(String spec) {
        if (spec == null)
            throw new NullPointerException("spec");

        if (spec.contains("://"))
            return true;

        if (spec.startsWith("/"))
            return true;

        return false;
    }

    @Override
    public String toString() {
        return "Context<" + name + ">";
    }

}
