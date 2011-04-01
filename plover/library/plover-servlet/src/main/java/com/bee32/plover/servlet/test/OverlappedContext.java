package com.bee32.plover.servlet.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.mortbay.jetty.servlet.Context;
import org.mortbay.log.Log;
import org.mortbay.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OverlappedContext
        extends Context {

    static Logger logger = LoggerFactory.getLogger(OverlappedContext.class);

    public OverlappedContext(int options) {
        super(options);
    }

    @Override
    public Resource getResource(String path)
            throws MalformedURLException {

        // logger.debug("OC::GET " + path);

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
