package com.bee32.zebra.oa.file;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.bodz.bas.http.HttpServlet;
import net.bodz.bas.http.ResourceTransferer;
import net.bodz.bas.http.servlet.FileAccessorServlet;
import net.bodz.bas.meta.codegen.CopyAndPaste;

@CopyAndPaste(FileAccessorServlet.class)
public class VirtualFileAccessorServlet
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * 1 day by default.
     */
    private int maxAge = 86400;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        FileManager fileManager = FileManager.forCurrentRequest();

        String pathInfo = req.getPathInfo();
        String path = fileManager.getStartDir().getPath();
        if (pathInfo != null)
            path += pathInfo;

        File file = new File(path);
        if (!file.exists()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (!file.canRead()) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Not readable.");
            return;
        }

        URL url = file.toURI().toURL();

        ResourceTransferer transferer = new ResourceTransferer(req, resp);
        transferer.setMaxAge(maxAge);
        transferer.transfer(url);
    }

}
