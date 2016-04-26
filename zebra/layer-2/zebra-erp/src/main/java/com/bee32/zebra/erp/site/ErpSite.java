package com.bee32.zebra.erp.site;

import net.bodz.bas.site.vhost.IVirtualHost;

import com.bee32.zebra.oa.site.OaSite;

/**
 * @label 主页
 */
public class ErpSite
        extends OaSite {

    public ErpSite(IVirtualHost vhost) {
        super(vhost);
    }

    @Override
    public String getSiteUrl() {
        return "http://zebra.bee32.com/erp";
    }

}
