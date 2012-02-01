package com.bee32.plover.faces;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

import org.apache.myfaces.view.facelets.BiResourceResolver;
import org.apache.myfaces.view.facelets.impl.DefaultResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassResourceResolver
        extends DefaultResourceResolver
        implements BiResourceResolver {

    static final Logger logger = LoggerFactory.getLogger(ClassResourceResolver.class);

    ClassLoader loader;

    Collection<URL> searchUrls;
    Collection<String> searchPaths;

    public ClassResourceResolver()
            throws IOException {
        loader = Thread.currentThread().getContextClassLoader();
        logger.info("Load facelets resources using class-loader " + loader);

        refreshResourceRoots();
    }

    protected void refreshResourceRoots()
            throws IOException {
        Enumeration<URL> resourceRootEnum = loader.getResources("resources/");

        searchUrls = new ArrayList<URL>();
        searchPaths = new ArrayList<String>();
        while (resourceRootEnum.hasMoreElements()) {
            URL url = resourceRootEnum.nextElement();

            logger.info("  Add resource search path: " + url);

            searchUrls.add(url);
            searchPaths.add(url.toString());
        }
    }

    @Override
    public URL resolveUrl(String resource) {
        String path = getResourcePath(resource);

        URL url = loader.getResource(path);

        if (url != null) {
            logger.debug("Custom RR " + path + " -> " + url);
            return url;
        } else {
            url = super.resolveUrl(resource);
            logger.debug("Default RR " + resource + " -> " + url);
            return url;
        }
    }

    protected String getResourcePath(String resource) {
        if (resource.startsWith("/"))
            resource = resource.substring(1);

        resource = "resources/" + resource;

        return resource;
    }

    @Override
    public String reverse(URL url) {
        if (url == null)
            return null;

        String path = url.toString();
        String reversed = null;

        for (String searchPath : searchPaths) {
            if (path.startsWith(searchPath)) {
                reversed = path.substring(searchPath.length());
                break;
            }
        }

        if (reversed != null)
            if (reversed.startsWith("/"))
                reversed = reversed.substring(1);

        logger.debug("  Reversed " + url + " to " + reversed);

        return reversed;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
