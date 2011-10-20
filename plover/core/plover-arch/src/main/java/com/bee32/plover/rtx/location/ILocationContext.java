package com.bee32.plover.rtx.location;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

public interface ILocationContext
        extends ITextForRequest {

    ILocationContext join(String location);

    /**
     * Resolve to location relative to the request url.
     *
     * @param request
     *            Non-<code>null</code> http servlet request object.
     * @return Non-<code>null</code> resolved location.
     */
    @Override
    @Deprecated
    String resolve(HttpServletRequest request);

    /**
     * Resolve to location relative to the servlet context.
     */
    String resolveContextRelative(HttpServletRequest request);

    String resolveAbsolute(HttpServletRequest request);

    /**
     * The resolved location is relative to the request context.
     *
     * @param request
     *            Non-<code>null</code> http servlet request object.
     * @return Non-<code>null</code> resolved location URI.
     */
    URI resolveURI(HttpServletRequest request)
            throws URISyntaxException;

    /**
     * The resolved location is relative to the request context.
     *
     * @param request
     *            Non-<code>null</code> http servlet request object.
     * @return Non-<code>null</code> resolved location URL.
     */
    URL resolveURL(HttpServletRequest request)
            throws MalformedURLException;

}
