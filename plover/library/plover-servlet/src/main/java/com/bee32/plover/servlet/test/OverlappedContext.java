package com.bee32.plover.servlet.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.mortbay.jetty.servlet.Context;
import org.mortbay.log.Log;
import org.mortbay.resource.Resource;

public class OverlappedContext
        extends Context {

    public OverlappedContext(int options) {
        super(options);
    }

    @Override
    public Resource getResource(String path)
            throws MalformedURLException {

        if (path == null || !path.startsWith("/"))
            throw new MalformedURLException(path);

        URL resourceUrl = OverlappedBases.searchResource(path);

        // Not in search-bases, fallback to the default one (which is resource-base based).
        if (resourceUrl == null)
            return super.getResource(path);

        Resource resource;
        try {
            resource = Resource.newResource(resourceUrl);
        } catch (IOException e) {
            Log.ignore(e);
            return null;
        }

        return resource;
    }

}
