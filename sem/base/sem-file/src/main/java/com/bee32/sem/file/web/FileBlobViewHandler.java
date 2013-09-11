package com.bee32.sem.file.web;

import java.io.IOException;

import com.bee32.plover.javascript.util.Javascripts;
import com.bee32.plover.orm.web.EntityHandler;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;
import com.bee32.sem.file.entity.FileBlob;

public class FileBlobViewHandler
        extends EntityHandler<FileBlob, String> {

    public FileBlobViewHandler(Class<FileBlob> entityType) {
        super(entityType);
    }

    @Override
    protected ActionResult _handleRequest(ActionRequest req, ActionResult result)
            throws IOException {

        String hash = req.getParameter("hash");

        if (hash == null)
            throw new IllegalArgumentException("Hash of blob isn't specified.");

        FileBlob blob = DATA(FileBlob.class).get(hash);
        if (blob == null)
            return Javascripts.alertAndBack("文件不存在: hash=" + hash).dump(result);

        req.setAttribute(HttpBlobDumper.ATTR_BLOB, blob);

        return HttpBlobDumper.dumpBlob(req.getActionName(), req, result.getResponse());
    }

}
