package com.bee32.plover.servlet.context;

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
    String resolve(HttpServletRequest request);

    /**
     * Resolve to location relative to the servl et context.
     * <p>
     * In default implementation, {@link #resolveAbsolute(HttpServletRequest)} returns the same
     * result as {@link #resolve(HttpServletRequest)}.
     */
    String resolveAbsolute(HttpServletRequest request);

    /**
     * The resolved location is relative to the request context.
     *
     * @param request
     *            Non-<code>null</code> http servlet request object.
     * @return Non-<code>null</code> resolved location URI.
     */
    URI resolveUri(HttpServletRequest request)
            throws URISyntaxException;

    /**
     * The resolved location is relative to the request context.
     *
     * @param request
     *            Non-<code>null</code> http servlet request object.
     * @return Non-<code>null</code> resolved location URL.
     */
    URL resolveUrl(HttpServletRequest request)
            throws MalformedURLException;

}
