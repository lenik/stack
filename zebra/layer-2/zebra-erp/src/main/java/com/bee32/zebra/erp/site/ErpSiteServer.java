package com.bee32.zebra.erp.site;

import net.bodz.bas.db.jdbc.DataSourceArguments;
import net.bodz.bas.site.vhost.MutableVirtualHost;
import net.bodz.bas.site.vhost.VirtualHostManager;
import net.bodz.uni.echo.config.EchoServerConfig;
import net.bodz.uni.echo.server.EchoServer;

public class ErpSiteServer {

    public static void main(String[] args)
            throws Exception {
        EchoServerConfig config = new ErpSiteServerConfig();
        EchoServer server = new EchoServer(config);

        VirtualHostManager vhosts = VirtualHostManager.getInstance();

        MutableVirtualHost vhost0 = new MutableVirtualHost();
        vhost0.setName("master");
        vhost0.addHostSpecifier("master.lo");

        DataSourceArguments jdbc0 = new DataSourceArguments();
        jdbc0.setServer("localhost:1063");
        jdbc0.setDatabase("devdb");
        vhost0.setAttribute(DataSourceArguments.ATTRIBUTE_KEY, jdbc0);

        MutableVirtualHost vhost1 = new MutableVirtualHost();
        vhost1.setName("foo");
        vhost1.addHostSpecifier("foo.lo");
        vhost1.addHostSpecifier("a.foo.lo");

        DataSourceArguments jdbc1 = new DataSourceArguments();
        jdbc1.setServer("localhost:1063");
        jdbc1.setDatabase("playdb");
        vhost1.setAttribute(DataSourceArguments.ATTRIBUTE_KEY, jdbc1);

        vhosts.add(vhost0);
        vhosts.add(vhost1);

        server.start();
    }

}
