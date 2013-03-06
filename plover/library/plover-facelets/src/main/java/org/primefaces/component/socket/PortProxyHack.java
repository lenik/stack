package org.primefaces.component.socket;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.servlet.util.ThreadHttpContext;

public class PortProxyHack {

    public static String portRewrite(String url) {
        HttpServletRequest request = ThreadHttpContext.getRequest();
        String xForwardedHost = request.getHeader("X-Forwarded-Host");
        if (xForwardedHost == null)
            return url;

        if (url.contains("://"))
            return url;

        int serverPort = request.getServerPort();
        url = "ws://" + xForwardedHost + ":" + serverPort + url;
        return url;
    }

}
