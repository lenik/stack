package com.bee32.zebra.tk.sql;

import net.bodz.bas.db.ctx.DataContext;
import net.bodz.bas.db.jdbc.ConnectOptions;
import net.bodz.bas.site.vhost.PostgresBackedVhostResolver;

public class ZebraVhostResolver
        extends PostgresBackedVhostResolver {

    static final ConnectOptions connectOptionsTemplate;
    static {
        connectOptionsTemplate = new ConnectOptions();
        connectOptionsTemplate.setServer("localhost:1063");
        connectOptionsTemplate.setDatabase("<not-set>");
        connectOptionsTemplate.setUserName("postgres");
        connectOptionsTemplate.setPassword("cW3EADp8");
    }

    public ZebraVhostResolver(DataContext master) {
        super(master, connectOptionsTemplate);
    }

}
