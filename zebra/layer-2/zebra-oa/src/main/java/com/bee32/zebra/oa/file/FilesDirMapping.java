package com.bee32.zebra.oa.file;

import net.bodz.bas.http.config.AbstractResourceMappings;
import net.bodz.bas.http.config.ServletContextConfig;
import net.bodz.bas.http.config.ServletDescriptor;
import net.bodz.bas.http.servlet.MappedFileAccessServlet;

public class FilesDirMapping
        extends AbstractResourceMappings {

    ServletContextConfig config;

    @Override
    public void servlets(ServletContextConfig config) {
        this.config = config;
        ZebraFilePathMapping mapping = ZebraFilePathMapping.getInstance();
        ServletDescriptor servlet = new ServletDescriptor(MappedFileAccessServlet.class);
        servlet.addMapping(mapping.getServletPath() + "/*");
        servlet.setInitParam(MappedFileAccessServlet.ATTRIBUTE_MAPPING_CLASS, //
                ZebraFilePathMapping.class.getName());
        servlet.install(config);
    }

}
