package com.bee32.zebra.tk.site;

import org.apache.ibatis.session.Configuration;

import net.bodz.bas.db.ibatis.AbstractIbatisConfigurer;

public class SiteVarsConfigurer
        extends AbstractIbatisConfigurer {

    @Override
    public void configure(Configuration config) {
        // Properties vars = new Properties();
        // vars.put("site", new SiteVariables());
        // config.setVariables(vars);
    }

}
