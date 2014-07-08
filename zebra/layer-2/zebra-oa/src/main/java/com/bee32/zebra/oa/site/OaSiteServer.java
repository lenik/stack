package com.bee32.zebra.oa.site;

import net.bodz.uni.echo.config.EchoServerConfig;
import net.bodz.uni.echo.server.EchoServer;

public class OaSiteServer {

    public static void main(String[] args)
            throws Exception {
        EchoServerConfig config = new OaSiteServerConfig();
        EchoServer server = new EchoServer(config);
        server.start();
    }

}
