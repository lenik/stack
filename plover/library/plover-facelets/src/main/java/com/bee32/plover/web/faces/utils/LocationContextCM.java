package com.bee32.plover.web.faces.utils;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.servlet.context.ILocationContext;
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
public class LocationContextCM
        extends VerbMap {

    private final ILocationContext location;

    static final int DEFAULT = 0;
    static final int ABSOLUTE = 1;
    static final int FULL_QUALIFIED = 2;

    private final int mode = DEFAULT;

    public LocationContextCM(ILocationContext locationContext) {
        this(locationContext, DEFAULT);
    }

    public LocationContextCM(ILocationContext locationContext, int mode) {
        if (locationContext == null)
            throw new NullPointerException("locationContext");
        this.location = locationContext;
    }

    @Override
    protected int getRequiredArguments(Object verb) {
        return 0;
    }

    static LocationContextCM create(ILocationContext locationContext, int mode) {
        return new LocationContextCM(locationContext, mode);
    }

    @Override
    protected Object execute(Object verb) {
        String locationSpec = String.valueOf(verb);

        if (locationSpec.startsWith("__")) {
            if ("__abs__".equals(locationSpec))
                return create(location, ABSOLUTE);
            else if ("__fqn__".equals(locationSpec))
                return create(location, FULL_QUALIFIED);
        }

        ILocationContext joined = location.join(locationSpec);
        return create(joined, mode);
    }

    @Override
    public String toString() {
        HttpServletRequest request = ThreadServletContext.requireRequest();

        switch (mode) {
        case ABSOLUTE:
            String requestRelative = location.resolve(request);
            return requestRelative;

        case FULL_QUALIFIED:
            StringBuilder sb = new StringBuilder();
            sb.append(request.getScheme());
            sb.append("://");
            sb.append(request.getServerName());
            if (request.getServerPort() != 80) {
                sb.append(':');
                sb.append(request.getServerPort());
            }

            // String requestRelative = location.resolve(request);
            return sb.toString();

        case DEFAULT:
        default:
            return location.resolve(request);
        }
    }
}
