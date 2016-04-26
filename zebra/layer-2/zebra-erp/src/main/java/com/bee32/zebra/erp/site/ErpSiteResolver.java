package com.bee32.zebra.erp.site;

import net.bodz.bas.repr.path.INoPathRef;
import net.bodz.bas.site.vhost.CurrentVirtualHost;
import net.bodz.bas.site.vhost.IVirtualHost;

public class ErpSiteResolver
        implements INoPathRef {

    @Override
    public ErpSite getTarget() {
        IVirtualHost vhost = CurrentVirtualHost.getVirtualHost();
        // vhost.getAttribute(ErpSite.class);
        return new ErpSite(vhost);
    }

}
