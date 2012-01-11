package com.bee32.sem.file.web;

import java.io.IOException;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.sem.file.dto.FileBlobDto;
import com.bee32.sem.file.entity.FileBlob;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class UploadFileBlobDialogBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(UploadFileBlobDialogBean.class);

    String caption = "Please select file(s) to upload...";

    // List<IncomingFile> incomingFiles = new ArrayList<IncomingFile>();

    public UploadFileBlobDialogBean() {
        super(FileBlob.class, FileBlobDto.class, 0);
    }

    public IncomingFile handleFileUpload(FileUploadEvent event)
            throws IOException {
        UploadedFile uploadedFile = event.getFile();
        IncomingFile incomingFile = new IncomingFile();
        incomingFile.setFileName(uploadedFile.getFileName());
        incomingFile.setSize(uploadedFile.getSize());
        incomingFile.setContentType(uploadedFile.getContentType());
        incomingFile.setInputStream(uploadedFile.getInputstream());
        return incomingFile;
    }

    // Properties

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

}
