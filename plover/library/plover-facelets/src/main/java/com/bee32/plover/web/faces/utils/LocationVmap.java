package com.bee32.plover.web.faces.utils;

import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.rtx.location.ILocationContext;
import com.bee32.plover.servlet.util.ThreadServletContext;

/**
 * Example usage:
 *
 * <pre>
 * WEB_APP                  /servlet
 * WEB_APP.__abs__          /servlet
 * WEB_APP.__fqn__          http://foo.bar/servlet
 * WEB_APP[spec]            /servlet/spec
 * WEB_APP.__abs__[spec]    /servlet/spec
 * WEB_APP.__fqn__[spec]    http://foo.bar/servlet/spec
 * </pre>
 */
public class LocationVmap
        extends VerbMap {

    private final ILocationContext location;

    static final int DEFAULT = 0;
    static final int CONTEXT_RELATIVE = 1;
    static final int ABSOLUTE = 2;
    static final int FULL_QUALIFIED = 3;

    private final int mode;

    public LocationVmap(ILocationContext locationContext) {
        this(locationContext, DEFAULT);
    }

    public LocationVmap(ILocationContext locationContext, int mode) {
        if (locationContext == null)
            throw new NullPointerException("locationContext");
        this.location = locationContext;
        this.mode = mode;
    }

    static LocationVmap create(ILocationContext locationContext, int mode) {
        return new LocationVmap(locationContext, mode);
    }

    @Override
    protected int getRequiredArguments(Object verb) {
        return 0;
    }

    public LocationVmap join(Object key) {
        return get(key);
    }

    @Override
    public LocationVmap get(Object key) {
        return (LocationVmap) super.get(key);
    }

    @Override
    protected Object execute(Object verb) {
        String locationSpec = String.valueOf(verb);

        if (locationSpec.startsWith("__")) {
            if ("__ctx__".equals(locationSpec))
                return create(location, CONTEXT_RELATIVE);
            else if ("__abs__".equals(locationSpec))
                return create(location, ABSOLUTE);
            else if ("__fqn__".equals(locationSpec))
                return create(location, FULL_QUALIFIED);
        }

        ILocationContext joined = location.join(locationSpec);
        return create(joined, mode);
    }

    @Override
    public String toString() {
        HttpServletRequest request = ThreadServletContext.getRequest();

        switch (mode) {
        case ABSOLUTE:
            return location.resolveAbsolute(request);

        case CONTEXT_RELATIVE:
            return location.resolveContextRelative(request);

        case FULL_QUALIFIED:
            try {
                return location.resolveURL(request).toString();
            } catch (MalformedURLException e) {
                return "<(error resolve url: " + e.getMessage() + ")>";
            }

        case DEFAULT:
        default:
            return location.resolveAbsolute(request);
        }
    }
}
