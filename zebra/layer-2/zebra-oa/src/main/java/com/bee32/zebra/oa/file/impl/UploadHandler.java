package com.bee32.zebra.oa.file.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.bee32.zebra.oa.file.FileManager;
import com.bee32.zebra.tk.site.IZebraSiteAnchors;

public class UploadHandler
        implements IZebraSiteAnchors {

    public UploadHandler() {
    }

    public UploadResult handlePostRequest(HttpServletRequest request)
            throws IOException {
        if (!ServletFileUpload.isMultipartContent(request))
            throw new IllegalArgumentException("Request is not multipart.");

        FileManager manager = FileManager.getInstance();
        ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());

        try {
            List<FileItem> items = uploadHandler.parseRequest(request);
            for (FileItem item : items) {
                if (item.isFormField())
                    continue;

                if (item.getName().isEmpty())
                    throw new IllegalArgumentException("empty filename.");
                File file = new File(manager.incomingDir, item.getName());
                item.write(file);
            }

            UploadResult result = new UploadResult();
            result.items = items;
            return result;
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

}