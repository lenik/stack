package com.bee32.zebra.erp.site;

import java.util.Locale;

import net.bodz.bas.http.config.ServletContextConfig;
import net.bodz.bas.xml.dom.XmlFormatter;
import net.bodz.uni.echo.server.EchoServer;

import com.bee32.zebra.tk.sql.TestEnvironment;

public class ErpSiteServer {

    public static void main(String[] args)
            throws Exception {
        XmlFormatter.NULL_VERBATIM = "<i class='fa fa-times zu-null'></i>";

        Locale.setDefault(Locale.SIMPLIFIED_CHINESE);

        ServletContextConfig config = new ErpSiteServerConfig();
        config.setPortNumber(8083);

        TestEnvironment.setUpVhosts();

        EchoServer server = new EchoServer(config);
        server.start();
    }

}
