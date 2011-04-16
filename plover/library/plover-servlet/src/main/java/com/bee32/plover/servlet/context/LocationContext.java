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

    protected final String name;
    protected final String base;

    public LocationContext(String name) {
        this.name = name;
        this.base = "";
    }

    public LocationContext(String name, String base) {
        this.name = name;
        this.base = base;
    }

    public String getName() {
        return name;
    }

    public String getBase() {
        return base;
    }

    protected abstract LocationContext create(String base);

    /**
     * Join two urls.
     *
     * @param base
     *            Non-<code>null</code> base url.
     * @param spec
     *            Non-<code>null</code> spec url.
     */
    protected String join(String base, String spec) {
        if (base == null)
            return spec;

        if (spec.equals("/"))
            if (base.endsWith("/"))
                return base;
            else
                return base + "/";

        else {
            while (spec.startsWith("/"))
                spec = spec.substring(1);

            return base + spec;
        }
    }

    @Override
    public LocationContext join(String location) {
        if (location == null)
            throw new NullPointerException("location");

        return create(join(base, location));
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
    public String resolveAbsolute(HttpServletRequest request) {
        return resolve(request);
    }

    @Override
    public URL resolveUrl(HttpServletRequest request)
            throws MalformedURLException {
        URL contextUrl = getContextUrl(request);
        return new URL(contextUrl, base);
    }

    @Override
    public final URI resolveUri(HttpServletRequest request)
            throws URISyntaxException {
        try {
            URL url = resolveUrl(request);
            return url.toURI();
        } catch (MalformedURLException e) {
            throw new URISyntaxException(base, e.getMessage());
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
        return "Context<" + name + ">" + " :: " + base;
    }

}
