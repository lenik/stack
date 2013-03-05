package org.primefaces.component.socket;

import com.bee32.plover.servlet.util.ThreadHttpContext;

public class PortProxyHack {

    public static String portRewrite(String url, String portNumber) {
        int css = url.indexOf("://");
        if (css == -1) {
            String serverName = ThreadHttpContext.getRequest().getServerName();
            int serverPort = ThreadHttpContext.getRequest().getServerPort();
            if (serverPort == 80) {
                url = "ws://" + serverName + ":" + portNumber + url;
            } else if (serverPort == 443) {
                url = "wss://" + serverName + ":" + portNumber + url;
            }
            return url; // no protocol name, ie., no hostname.
        }

        String protocol = url.substring(0, css);
        String host_ = url.substring(css + 3);

        int slash = host_.indexOf('/');
        String hostPort = host_.substring(0, slash);

        String _path_ = slash == -1 ? "" : host_.substring(slash);

        int colon = hostPort.indexOf(':');
        String host = colon == -1 ? hostPort : hostPort.substring(0, colon);

        url = protocol + "://" + host + ":" + portNumber + _path_;
        return url;
    }

}
