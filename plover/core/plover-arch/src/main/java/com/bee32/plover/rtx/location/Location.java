package com.bee32.plover.rtx.location;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

public abstract class Location
        implements ILocationContext, Serializable {

    private static final long serialVersionUID = 1L;

    protected final String name;
    protected final String base;
    protected final boolean directory;

    Location(String name) {
        this.name = name;
        this.base = null;
        this.directory = false;
    }

    public Location(String name, String base) {
        this.name = name;

        if (base == null)
            this.directory = false;
        else {
            this.directory = base.endsWith("/");
            if (directory)
                base = base.substring(0, base.length() - 1);
        }
        this.base = base;
    }

    public String getName() {
        return name;
    }

    public String getBase() {
        return base;
    }

    public boolean isDirectory() {
        return directory;
    }

    protected abstract Location create(String base);

    protected final String join(StringBuffer context, String spec) {
        boolean isdir = false;
        int len = context.length();
        if (len > 0 && context.charAt(len - 1) == '/') {
            context.setLength(--len);
            isdir = true;
        }
        return join(context, isdir, spec);
    }

    /**
     * Join urls in relative-only mode.
     *
     * <pre>
     * foo? + null  => foo
     * foo? + ""    => foo
     * foo? + /bar  => foo/bar
     * foo? + bar   => foo?bar
     * </pre>
     *
     * @param context
     *            context url. <code>null</code> to return the spec.
     * @param isdir
     *            Whether context is ended with /.
     * @param spec
     *            spec url, <code>null</code> to return the context path.
     */
    protected String join(String context, boolean isdir, String spec) {
        if (context == null)
            return spec;

        if (spec == null || spec.isEmpty())
            return context;

        if (spec.startsWith("/"))
            return context + spec;

        if (isdir)
            return context + "/" + spec;
        else
            return context + spec;
    }

    /**
     * Join urls in relative-only mode.
     *
     * <pre>
     * foo? + null  => foo?
     * foo? + /bar  => foo/bar
     * foo? + bar   => foo?bar
     * foo? + ""    => foo?
     * </pre>
     *
     * @param buffer
     *            Non-<code>null</code> context url in the string buffer.
     * @param spec
     *            spec url, <code>null</code> to use the context path.
     */
    protected String join(StringBuffer buffer, boolean isdir, String spec) {
        if (spec == null || spec.isEmpty())
            return buffer.toString();

        if (spec.startsWith("/"))
            buffer.append(spec);
        else {
            if (isdir)
                buffer.append('/');
            buffer.append(spec);
        }
        return buffer.toString();
    }

    @Override
    public Location join(String location) {
        if (location == null)
            throw new NullPointerException("location");

        return create(join(base, directory, location));
    }

    protected StringBuffer getContext(HttpServletRequest request) {
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
        sb.append('/');
        int jointPos = sb.length();

        getContext(sb, request);
        if (sb.length() > jointPos) {
            // FIX: http://foo:80/ + / => http://foo/80/
            boolean twice = sb.charAt(jointPos) == '/';

            if (twice)
                sb.deleteCharAt(jointPos); // Uncessary '/'.
        }

        return sb;
    }

    @Override
    @Deprecated
    public String resolve(HttpServletRequest request) {
        return resolveContextRelative(request);
    }

    @Override
    public String resolveAbsolute(HttpServletRequest request) {
        StringBuffer context = getContext(request);
        String contextRelative = resolveContextRelative(request);
        if (context == null) {
            return contextRelative;
        } else {
            String absolute = join(context, contextRelative);
            return absolute;
        }
    }

    @Override
    public String resolveContextRelative(HttpServletRequest request) {
        return base;
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
