package com.bee32.sem.file.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.web.faces.controls2.IDialogCallback;
import com.bee32.sem.file.dto.FileBlobDto;
import com.bee32.sem.file.entity.FileBlob;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class UploadFileBlobDialogBean
        extends SimpleEntityViewBean
        implements IDialogCallback {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(UploadFileBlobDialogBean.class);

    String caption = "Please select file(s) to upload...";

    public UploadFileBlobDialogBean() {
        super(FileBlob.class, FileBlobDto.class, 0);
    }

    @Override
    public void submit() {
        logger.debug("Selected: " + getSelection());
    }

    @Override
    public void cancel() {
        logger.debug("Cancelled.");
    }

    // Properties

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

}
