package com.bee32.plover.servlet.context;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

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
     * Join urls in relative-only mode.
     *
     * <pre>
     * foo/ + null  => foo/
     * foo  + null  => foo
     * foo/ + /     => foo/
     * foo  + /     => foo/
     *
     * foo  + /bar  => foo/bar
     * foo  + bar   => foobar
     * foo/ + /bar  => foo/bar
     * foo/ + bar   => foo/bar
     * </pre>
     *
     * @param context
     *            Non-<code>null</code> base url.
     * @param spec
     *            spec url, <code>null</code> to return the context path.
     */
    protected String join(String context, String spec) {
        if (spec == null)
            return context;

        if (spec.equals("/")) {
            if (context.endsWith("/"))
                return context;
            else
                return context + "/";
        }

        if (context.endsWith("/") && spec.startsWith("/"))
            spec = spec.substring(1);

        return context + spec;
    }

    /**
     * Join urls in relative-only mode.
     *
     * <pre>
     * foo/ + null  => foo/
     * foo  + null  => foo
     * foo/ + /     => foo/
     * foo  + /     => foo/
     *
     * foo  + /bar  => foo/bar
     * foo  + bar   => foobar
     * foo/ + /bar  => foo/bar
     * foo/ + bar   => foo/bar
     * </pre>
     *
     * @param buffer
     *            Non-<code>null</code> base url in the string buffer.
     * @param spec
     *            spec url, <code>null</code> to use the context path.
     */
    protected String join(StringBuffer buffer, String spec) {
        if (spec != null) {
            int len = buffer.length();
            boolean contextSlash = len > 0 && buffer.charAt(len - 1) == '/';

            if (spec.equals("/")) {
                if (!contextSlash)
                    buffer.append('/');
            }

            else if (contextSlash && spec.startsWith("/"))
                spec = spec.substring(1);

            buffer.append(spec);
        }
        return buffer.toString();
    }

    @Override
    public LocationContext join(String location) {
        if (location == null)
            throw new NullPointerException("location");

        return create(join(base, location));
    }

    protected final StringBuffer getContext(HttpServletRequest request) {
        StringBuffer buffer = new StringBuffer();
        getContext(buffer, request);
        return buffer;
    }

    protected abstract void getContext(StringBuffer sb, HttpServletRequest request);

    /**
     * Get the base URL of this context.
     *
     * @return Non-<code>null</code> context URL, or <code>null</code> if this context can't be
     *         converted to a URL.
     */
    protected StringBuffer getContextURL(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();
        sb.append(request.getScheme());
        sb.append("://");
        sb.append(request.getServerName());
        if (request.getServerPort() != 80) {
            sb.append(':');
            sb.append(request.getServerPort());
        }
        getContext(sb, request);
        return sb;
    }

    @Override
    public String resolve(HttpServletRequest request) {
        return resolveAbsolute(request);
    }

    @Override
    public String resolveAbsolute(HttpServletRequest request) {
        StringBuffer context = getContext(request);
        return join(context, base);
    }

    @Override
    public URL resolveURL(HttpServletRequest request)
            throws MalformedURLException {
        StringBuffer urlBuffer = getContextURL(request);
        join(urlBuffer, base);
        return new URL(urlBuffer.toString());
    }

    @Override
    public final URI resolveURI(HttpServletRequest request)
            throws URISyntaxException {
        try {
            URL url = resolveURL(request);
            return url.toURI();
        } catch (MalformedURLException e) {
            throw new URISyntaxException(base, e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Context<" + name + ">" + " :: " + base;
    }

}
