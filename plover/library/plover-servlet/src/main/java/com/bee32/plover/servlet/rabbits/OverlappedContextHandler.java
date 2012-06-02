package com.bee32.plover.servlet.rabbits;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;

public class OverlappedContextHandler
        extends ServletContextHandler {

    // static Logger logger = LoggerFactory.getLogger(OverlappedContextHandler.class);
    Logger logger = Log.getLogger(OverlappedContextHandler.class);

    public OverlappedContextHandler(int options) {
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
            logger.ignore(e);
            return null;
        }

        return resource;
    }

}
