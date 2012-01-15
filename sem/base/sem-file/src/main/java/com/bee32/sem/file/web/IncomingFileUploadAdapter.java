package com.bee32.sem.file.web;

import java.io.IOException;
import java.util.List;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.bee32.plover.collections.Varargs;

public abstract class IncomingFileUploadAdapter {

    public void handleFileUpload(FileUploadEvent event)
            throws IOException {
        UploadedFile uploadedFile = event.getFile();
        IncomingFile incomingFile = new IncomingFile();
        incomingFile.setFileName(uploadedFile.getFileName());
        incomingFile.setSize(uploadedFile.getSize());
        incomingFile.setContentType(uploadedFile.getContentType());
        incomingFile.setInputStream(uploadedFile.getInputstream());
        process(Varargs.toList(incomingFile));
    }

    protected abstract void process(List<IncomingFile> incomingFiles)
            throws IOException;

}
