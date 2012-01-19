package com.bee32.sem.file.web;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.bee32.sem.file.entity.FileBlob;

public abstract class IncomingFileBlobAdapter
        extends IncomingFileUploadAdapter {

    protected abstract void process(FileBlob fileBlob, IncomingFile incomingFile)
            throws IOException;

    @Override
    protected void process(List<IncomingFile> incomingFiles)
            throws IOException {
        for (IncomingFile incomingFile : incomingFiles) {
            File localFile;
            try {
                localFile = incomingFile.saveToLocalFile();
            } catch (IOException e) {
                reportError("传输文件失败: " + incomingFile.getFileName(), e);
                continue;
            }

            FileBlob fileBlob;
            try {
                fileBlob = FileBlob.commit(localFile, true);
            } catch (IOException e) {
                reportError("无法将接收的文件保存至存储系统", e);
                continue;
            }

            // assert fileBlob.getLength() == incomingFile.getSize();
            fileBlob.setContentType(incomingFile.getContentType());

            process(fileBlob, incomingFile);
        }
    }

    protected void reportError(String message, Exception exception) {
        exception.printStackTrace();
    }

}
