package com.bee32.plover.web.faces;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.facelets.impl.DefaultResourceResolver;

public class CustomResourceResolver
        extends DefaultResourceResolver {

    static final Logger logger = LoggerFactory.getLogger(CustomResourceResolver.class);

    ClassLoader loader;

    public CustomResourceResolver() {
        loader = Thread.currentThread().getContextClassLoader();
    }

    @Override
    public URL resolveUrl(String resource) {

        logger.info("CRR-resoluve: " + resource);

        URL url = super.resolveUrl(resource);

        if (url == null) {
            if (resource.startsWith("/"))
                resource = resource.substring(1);

            resource = "res/" + resource;

            url = loader.getResource(resource);
        }
        return url;
    }

}
