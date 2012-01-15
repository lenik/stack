package com.bee32.sem.file.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.web.faces.view.ViewBean;

public class UploadFileDialogBean
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(UploadFileDialogBean.class);

    String caption = "Please select file(s) to upload...";

    // Properties

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

}
