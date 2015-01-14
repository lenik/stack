package com.bee32.zebra.oa.site;

import net.bodz.bas.http.config.ServletContextConfig;
import net.bodz.uni.echo.test.AbstractWebAppTester;

public class OaSiteTester
        extends AbstractWebAppTester {

    @Override
    protected ServletContextConfig createConfig() {
        return new OaSiteServerConfig();
    }

    public static void main(String[] args)
            throws Exception {
        new OaSiteTester().makeClient().go("");
    }

}
