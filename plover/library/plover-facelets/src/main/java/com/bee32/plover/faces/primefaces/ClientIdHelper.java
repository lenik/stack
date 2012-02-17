package com.bee32.plover.faces.primefaces;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import javax.faces.component.UIComponent;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.DefaultClassLoader;
import com.bee32.plover.servlet.util.ThreadHttpContext;

public class ClientIdHelper {

    static Logger logger = LoggerFactory.getLogger(ClientIdHelper.class);

    public static void reportBadClientId(UIComponent user, String id) {
        System.err.println("-- Bad client id " + id + " used by the component: " + user.getClientId());

        HttpServletRequest req = ThreadHttpContext.getRequest();

        String servletPath = req.getServletPath();
        String path = servletPath.replace(".jsf", ".xhtml");
        path = "resources" + path;

        URL resource = DefaultClassLoader.getInstance().getResource(path);
        if (!"file".equals(resource.getProtocol()))
            return;

        File file;
        try {
            file = new File(resource.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        path = file.getPath();
        path = path.replace("/target/classes/", "/src/main/resources/");
        path = path.replace("/target/test-classes/", "/src/test/resources/");

        int srcPos = path.indexOf("/src/main/");
        if (srcPos == -1)
            srcPos = path.indexOf("/src/test/");
        if (srcPos == -1)
            srcPos = path.indexOf("/src/");
        if (srcPos != -1) {
            int modPos = path.lastIndexOf('/', srcPos - 1);
            if (modPos != -1)
                path = path.substring(modPos);
            else
                path = path.substring(srcPos);
        }

        System.err.println("-- In this page: \n" + path);
    }

}
