package com.bee32.zebra.tk.sql;

import net.bodz.bas.db.ctx.DataContext;
import net.bodz.bas.db.ctx.JdbcDataContexts;
import net.bodz.bas.db.jdbc.ConnectOptions;
import net.bodz.bas.site.vhost.MutableVirtualHost;
import net.bodz.bas.site.vhost.MutableVirtualHostResolver;
import net.bodz.bas.site.vhost.PostgresBackedVhostResolver;
import net.bodz.bas.site.vhost.VirtualHostManager;

public class TestEnvironment {

    static VirtualHostManager manager = VirtualHostManager.getInstance();
    static MutableVirtualHostResolver resolver1 = new MutableVirtualHostResolver();
    static PostgresBackedVhostResolver resolver2;

    public static void setUpVhosts() {
        MutableVirtualHost master = new MutableVirtualHost();
        {
            ConnectOptions masterdb = new ConnectOptions();
            master.setName("master");
            master.addHostSpecifier("master.lo");
            master.setAttribute(ConnectOptions.ATTRIBUTE_KEY, masterdb);
            masterdb.setServer("localhost:1063");
            masterdb.setDatabase("postgres");
            masterdb.setUserName("postgres");
            masterdb.setPassword("cW3EADp8");
            resolver1.add(master);

            DataContext masterDataSource = JdbcDataContexts.getInstance().get(masterdb);
            resolver2 = new ZebraVhostResolver(masterDataSource);
        }

        MutableVirtualHost play = new MutableVirtualHost();
        {
            ConnectOptions playdb = new ConnectOptions();
            play.setName("play");
            play.addHostSpecifier("play.lo");
            play.addHostSpecifier("a.play.lo");
            play.setAttribute(ConnectOptions.ATTRIBUTE_KEY, playdb);
            playdb.setServer("localhost:1063");
            playdb.setDatabase("playdb");
            playdb.setUserName("play");
            playdb.setPassword("yalp");
            resolver1.add(play);
        }

        MutableVirtualHost demo = new MutableVirtualHost();
        {
            ConnectOptions demodb = new ConnectOptions();
            demo.setName("demo");
            demo.addHostSpecifier("demo.*");
            demo.setAttribute(ConnectOptions.ATTRIBUTE_KEY, demodb);
            demodb.setServer("localhost:1063");
            demodb.setDatabase("demodb");
            demodb.setUserName("semsadmin");
            demodb.setPassword("MxDkUWl1");
            resolver1.add(demo);
            // resolver1.setDefault(demo);
        }

        manager.add(resolver1);
        manager.add(resolver2);
    }

}
