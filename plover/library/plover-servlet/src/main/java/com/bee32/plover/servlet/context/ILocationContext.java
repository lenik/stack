package com.bee32.plover.servlet.context;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

public interface ILocationContext {

    ContextLocation join(String location);

    /**
     * The resolved location is relative to this context.
     *
     * @return Non-<code>null</code> resolved location.
     */
    String resolve(HttpServletRequest request, String location);

    URI resolveUri(HttpServletRequest request, String location)
            throws URISyntaxException;

    URL resolveUrl(HttpServletRequest request, String location)
            throws MalformedURLException;

}
