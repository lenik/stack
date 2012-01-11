package com.bee32.sem.file.web;

import java.io.IOException;
import java.util.List;

import org.primefaces.event.FileUploadEvent;

import com.bee32.plover.arch.operation.Operation;
import com.bee32.plover.collections.Varargs;
import com.bee32.plover.web.faces.utils.FacesContextSupport;

public abstract class UploadFileBlobDialogListener
        extends FacesContextSupport {

    protected UploadFileBlobDialogBean dialogBean;

    @Operation
    public void handleFileUpload(FileUploadEvent event)
            throws IOException {
        dialogBean = getBean(UploadFileBlobDialogBean.class);
        IncomingFile incomingFile = dialogBean.handleFileUpload(event);
        process(Varargs.toList(incomingFile));
    }

    protected abstract void process(List<IncomingFile> incomingFiles)
            throws IOException;

}
