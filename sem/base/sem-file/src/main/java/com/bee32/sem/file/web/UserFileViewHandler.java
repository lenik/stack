package com.bee32.sem.file.web;

import java.io.IOException;

import javax.servlet.ServletException;

import com.bee32.plover.orm.web.EntityHandler;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;
import com.bee32.sem.file.entity.FileBlob;
import com.bee32.sem.file.entity.UserFile;

public class UserFileViewHandler
        extends EntityHandler<UserFile, Long> {

    public UserFileViewHandler(Class<UserFile> entityType) {
        super(entityType);
    }

    @Override
    protected ActionResult _handleRequest(ActionRequest req, ActionResult result)
            throws IOException, ServletException {

        String _id = req.getParameter("id");
        if (_id == null)
            throw new IllegalArgumentException("User file id isn't specified.");

        long id = Long.parseLong(_id);
        UserFile userFile = asFor(UserFile.class).getOrFail(id);
        if (userFile == null)
            throw new ServletException("User file with id=" + id + " isn't existed.");

        FileBlob blob = userFile.getFileBlob();
        String filename = userFile.getName();
        String description = userFile.getLabel();

        req.setAttribute(HttpBlobDumper.ATTR_BLOB, blob);
        req.setAttribute(HttpBlobDumper.ATTR_FILENAME, filename);
        req.setAttribute(HttpBlobDumper.ATTR_DESCRIPTION, description);

        return HttpBlobDumper.dumpBlob(req, result);
    }

}
