package com.bee32.zebra.oa.file;

import java.io.File;

import net.bodz.bas.c.system.SystemProperties;
import net.bodz.bas.http.config.AbstractResourceMappings;
import net.bodz.bas.http.config.ServletContextConfig;

public class FilesDirMapping
        extends AbstractResourceMappings {

    ServletContextConfig config;

    @Override
    public void servlets(ServletContextConfig config) {
        this.config = config;
        String homeDir = SystemProperties.getUserHome();
        File filesDir = new File(homeDir, "files");
        localLink("/files", filesDir.getPath(), 365).install(config);
    }

}
