package com.bee32.zebra.tk.sql;

import net.bodz.bas.db.jdbc.DataSourceArguments;
import net.bodz.bas.site.vhost.MutableVirtualHost;
import net.bodz.bas.site.vhost.VirtualHostManager;

public class TestEnvironment {

    public static void setUpVhosts() {

        DataSourceArguments devdb = new DataSourceArguments();
        devdb.setServer("localhost:1063");
        devdb.setDatabase("devdb");
        devdb.setUserName("postgres");
        devdb.setPassword("cW3EADp8");

        DataSourceArguments playdb = new DataSourceArguments();
        playdb.setServer("localhost:1063");
        playdb.setDatabase("playdb");
        playdb.setUserName("play");
        playdb.setPassword("yalp");

        DataSourceArguments zjhfdb = new DataSourceArguments();
        zjhfdb.setServer("localhost:1063");
        zjhfdb.setDatabase("zjhf_db");
        zjhfdb.setUserName("postgres");
        zjhfdb.setPassword("cW3EADp8");

        VirtualHostManager vhosts = VirtualHostManager.getInstance();

        MutableVirtualHost vhost0 = new MutableVirtualHost();
        vhost0.setName("master");
        vhost0.addHostSpecifier("master.lo");
        vhost0.setAttribute(DataSourceArguments.ATTRIBUTE_KEY, zjhfdb);

        MutableVirtualHost vhost1 = new MutableVirtualHost();
        vhost1.setName("foo");
        vhost1.addHostSpecifier("foo.lo");
        vhost1.addHostSpecifier("a.foo.lo");
        vhost1.setAttribute(DataSourceArguments.ATTRIBUTE_KEY, playdb);

        vhosts.add(vhost0);
        vhosts.add(vhost1);

    }

}
