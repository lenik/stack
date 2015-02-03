package com.bee32.zebra.erp.site;

import com.bee32.zebra.oa.site.OaSite;

/**
 * @label 主页
 */
public class ErpSite
        extends OaSite {

    public ErpSite() {
    }

    @Override
    public String getSiteUrl() {
        return "http://zebra.bee32.com/erp";
    }

}
