package com.bee32.zebra.oa.site;

import net.bodz.bas.http.config.ServletContextConfig;
import net.bodz.uni.echo.server.EchoServer;

public class OaSiteServer {

    public static void main(String[] args)
            throws Exception {
        ServletContextConfig config = new OaSiteServerConfig();
        EchoServer server = new EchoServer(config);
        server.start();
    }

}
