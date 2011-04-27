package com.bee32.plover.servlet.context;

import java.net.MalformedURLException;
import java.net.URL;

import javax.free.IllegalUsageException;
import javax.free.UnexpectedException;
import javax.servlet.http.HttpServletRequest;

public class PredefinedContextLocation
        extends Location {

    private static final long serialVersionUID = 1L;

    private final URL root;

    public PredefinedContextLocation(String name, String rootPath) {
        this(name, _parse(rootPath), null);
    }

    static URL _parse(String rootPath) {
        if (rootPath == null)
            throw new NullPointerException("rootPath");

        try {
            return new URL(rootPath);
        } catch (MalformedURLException e) {
            throw new IllegalUsageException(e.getMessage(), e);
        }
    }

    public PredefinedContextLocation(String name, URL root, String base) {
        super(name, base);

        if (root == null)
            throw new NullPointerException("root");

        this.root = root;
    }

    @Override
    protected Location create(String base) {
        return new PredefinedContextLocation(name, root, base);
    }

    @Override
    public PredefinedContextLocation join(String location) {
        return (PredefinedContextLocation) super.join(location);
    }

    public PredefinedContextLocation merge() {
        try {
            URL newRoot = new URL(root, base);
            return new PredefinedContextLocation(name + " | " + base, newRoot, null);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    @Override
    protected void getContext(StringBuffer sb, HttpServletRequest request) {
        throw new UnexpectedException("Not Used");
    }

    @Override
    public String resolveAbsolute(HttpServletRequest request) {
        try {
            URL url = resolveURL(request);
            return url.toString();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    @Override
    public URL resolveURL(HttpServletRequest request)
            throws MalformedURLException {
        if (base == null)
            return root;
        else
            return new URL(root, base);
    }

}
