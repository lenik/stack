package com.bee32.plover.servlet.context;

import java.net.MalformedURLException;
import java.net.URL;

import javax.free.IllegalUsageException;
import javax.servlet.http.HttpServletRequest;

public class PredefinedLocationContext
        extends LocationContext {

    private static final long serialVersionUID = 1L;

    private final URL root;

    public PredefinedLocationContext(String name, String rootPath) {
        this(name, _parse(rootPath));
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

    public PredefinedLocationContext(String name, URL root) {
        this(name, root, null);
    }

    public PredefinedLocationContext(String name, URL root, String base) {
        super(name, base);

        if (root == null)
            throw new NullPointerException("root");

        this.root = root;
    }

    @Override
    protected LocationContext create(String base) {
        return new PredefinedLocationContext(name, root, base);
    }

    @Override
    public String resolve(HttpServletRequest request) {
        try {
            return resolveUrl(request).toString();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    @Override
    public URL resolveUrl(HttpServletRequest request)
            throws MalformedURLException {
        if (base == null)
            return root;
        else
            return new URL(root, base);
    }

}
