package com.bee32.zebra.oa.file;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import net.bodz.bas.c.system.SystemProperties;
import net.bodz.bas.err.IllegalUsageException;
import net.bodz.bas.http.ctx.IAnchor;
import net.bodz.bas.site.file.AbstractFilePathMapping;
import net.bodz.bas.site.vhost.IVirtualHost;
import net.bodz.bas.site.vhost.VirtualHostManager;

import com.bee32.zebra.tk.site.IZebraSiteAnchors;

public class ZebraFilePathMapping
        extends AbstractFilePathMapping
        implements IZebraSiteAnchors {

    public static final String ATTRIBUTE_KEY = ZebraFilePathMapping.class.getName();

    private static String startDir = "files";

    File homeDir = new File(SystemProperties.getUserHome());

    @Override
    public File getLocalRoot(HttpServletRequest req) {
        IVirtualHost vhost = VirtualHostManager.getInstance().resolve(req);
        if (vhost == null)
            throw new IllegalUsageException("No file available, the virtual host isn't defined.");
        String name = vhost.getName();
        String prefix = startDir + "/" + name;
        return new File(homeDir, prefix);
    }

    @Override
    public String getServletPath() {
        return "/" + startDir;
    }

    @Override
    public IAnchor getRootAnchor() {
        return _webApp_.join(startDir + "/");
    }

    static final ZebraFilePathMapping instance = new ZebraFilePathMapping();

    public static ZebraFilePathMapping getInstance() {
        return instance;
    }

}
