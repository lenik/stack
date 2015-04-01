package com.bee32.zebra.oa.file;

import java.io.File;

import net.bodz.bas.c.system.SystemProperties;
import net.bodz.bas.err.IllegalRequestException;
import net.bodz.bas.site.vhost.CurrentVirtualHost;
import net.bodz.bas.site.vhost.IVirtualHost;

public class FileManager {

    public static final String ATTRIBUTE_KEY = FileManager.class.getName();

    public String prefix = "files/";
    public String incomingPrefix;

    public File startDir;
    public File incomingDir;

    public FileManager(String appId) {
        if (appId != null)
            prefix += appId + "/";
        incomingPrefix = prefix + "incoming/";

        File homeDir = new File(SystemProperties.getUserHome());
        startDir = new File(homeDir, prefix);
        startDir.mkdirs();

        incomingDir = new File(homeDir, incomingPrefix);
        incomingDir.mkdirs();
    }

    public File getFile(String path) {
        return new File(startDir, path);
    }

    public static FileManager forCurrentRequest() {
        IVirtualHost vhost = CurrentVirtualHost.getVirtualHostOpt();
        if (vhost == null)
            throw new IllegalRequestException("Virtual host is undefined.");
        else
            return forVirtualHost(vhost);
    }

    public static synchronized FileManager forVirtualHost(IVirtualHost virtualHost) {
        FileManager fileManager = virtualHost.getAttribute(FileManager.ATTRIBUTE_KEY);
        if (fileManager == null) {
            String name = virtualHost.getName();
            fileManager = new FileManager(name);
            virtualHost.setAttribute(FileManager.ATTRIBUTE_KEY, fileManager);
        }
        return fileManager;
    }

}
