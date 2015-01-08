package com.bee32.zebra.erp.site;

import net.bodz.bas.html.servlet.PathDispatchServlet;

import com.bee32.zebra.oa.site.OaSiteServerConfig;

public class ErpSiteServerConfig
        extends OaSiteServerConfig {

    @Override
    protected void configServlets() {
        super.configServlets();
        dispatching.setInitParam(PathDispatchServlet.ROOT_CLASS, //
                ErpSiteResolver.class.getName());
    }

}
