package com.bee32.zebra.oa.file;

import net.bodz.bas.http.config.AbstractResourceMappings;
import net.bodz.bas.http.config.ServletContextConfig;
import net.bodz.bas.http.config.ServletDescriptor;

public class FilesDirMapping
        extends AbstractResourceMappings {

    ServletContextConfig config;

    @Override
    public void servlets(ServletContextConfig config) {
        this.config = config;

        ServletDescriptor servlet = new ServletDescriptor(VirtualFileAccessorServlet.class);
        servlet.addMapping("/files/*");
        servlet.install(config);
    }

}
