package com.bee32.sem.file.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bee32.plover.orm.web.EntityServlet;
import com.bee32.sem.file.entity.FileBlob;
import com.bee32.sem.file.entity.UserFile;

public class UserFileServlet
        extends EntityServlet {

    private static final long serialVersionUID = 1L;

    /**
     * view/id/...
     *
     * download/id/...
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        assert pathInfo.startsWith("/");

        int nextSlash = pathInfo.indexOf('/', 1);
        if (nextSlash == -1) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String mode = pathInfo.substring(1, nextSlash);

        pathInfo = pathInfo.substring(nextSlash + 1);
        nextSlash = pathInfo.indexOf('/');
        String key;
        if (nextSlash == -1)
            key = pathInfo;
        else
            key = pathInfo.substring(0, nextSlash);

        long id = Long.parseLong(key);

        UserFile userFile = DATA(UserFile.class).getOrFail(id);
        if (userFile == null)
            throw new ServletException("User file with id=" + id + " isn't existed.");

        FileBlob blob = userFile.getFileBlob();
        String filename = userFile.getName();
        String description = userFile.getLabel();

        req.setAttribute(HttpBlobDumper.ATTR_BLOB, blob);
        req.setAttribute(HttpBlobDumper.ATTR_FILENAME, filename);
        req.setAttribute(HttpBlobDumper.ATTR_DESCRIPTION, description);

        HttpBlobDumper.dumpBlob(mode, req, resp);

        super.doGet(req, resp);
    }

}
