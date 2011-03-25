package com.bee32.plover.servlet.context;

import java.net.MalformedURLException;
import java.net.URL;

import javax.free.IllegalUsageException;
import javax.servlet.http.HttpServletRequest;

public class PredefinedLocationContext
        extends LocationContext {

    private static final long serialVersionUID = 1L;

    private final URL base;

    public PredefinedLocationContext(String name, String base) {
        this(name, _parse(base));
    }

    static URL _parse(String base) {
        try {
            return new URL(base);
        } catch (MalformedURLException e) {
            throw new IllegalUsageException(e.getMessage(), e);
        }
    }

    public PredefinedLocationContext(String name, URL base) {
        super("<#" + name + ">");
        this.base = base;
    }

    @Override
    public String resolve(HttpServletRequest request, String location) {
        try {
            return resolveUrl(request, location).toString();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    @Override
    public URL resolveUrl(HttpServletRequest request, String location)
            throws MalformedURLException {
        return new URL(base, location);
    }

}
