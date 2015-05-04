package com.bee32.zebra.oa.file;

import java.io.File;

import net.bodz.bas.c.string.Strings;
import net.bodz.bas.c.system.SystemProperties;
import net.bodz.bas.err.IllegalRequestException;
import net.bodz.bas.http.ctx.IAnchor;
import net.bodz.bas.site.vhost.CurrentVirtualHost;
import net.bodz.bas.site.vhost.IVirtualHost;

import com.bee32.zebra.tk.site.IZebraSiteAnchors;

public class FileManager
        implements IZebraSiteAnchors {

    public static final String ATTRIBUTE_KEY = FileManager.class.getName();

    private String prefix = "files/";
    private File startDir;

    public FileManager(String appId) {
        if (appId != null)
            prefix += appId + "/";

        File homeDir = new File(SystemProperties.getUserHome());
        startDir = new File(homeDir, prefix);
        startDir.mkdirs();
    }

    public File getStartDir() {
        return startDir;
    }

    public File getDir(Class<?> clazz) {
        String schema = clazz.getSimpleName();
        schema = Strings.lcfirst(schema);
        return getDir(schema);
    }

    public File getDir(String schema) {
        File schemaDir = new File(startDir, schema);
        schemaDir.mkdirs();
        return schemaDir;
    }

    public File getFile(Class<?> clazz, String path) {
        File schemaDir = getDir(clazz);
        return new File(schemaDir, path);
    }

    public File getFile(String schema, String path) {
        File schemaDir = getDir(schema);
        return new File(schemaDir, path);
    }

    public IAnchor getAnchor(Class<?> clazz) {
        String schema = clazz.getSimpleName();
        schema = Strings.lcfirst(schema);
        return getAnchor(schema);
    }

    public IAnchor getAnchor(String schema) {
        return _webApp_.join("file/" + schema);
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
