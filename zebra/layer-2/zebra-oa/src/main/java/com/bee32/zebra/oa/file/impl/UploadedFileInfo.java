package com.bee32.zebra.oa.file.impl;

import org.apache.commons.fileupload.FileItem;

public class UploadedFileInfo {

    public String name;
    public long size;
    public String url;
    public String thumbnail;
    public String deleteUrl;
    public String deleteType = "DELETE";

    public UploadedFileInfo(FileItem item) {
        name = item.getName();
        size = item.getSize();
    }

}