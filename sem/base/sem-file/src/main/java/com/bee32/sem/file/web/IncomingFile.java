package com.bee32.sem.file.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.free.IllegalUsageException;
import javax.free.InputStreamSource;
import javax.free.JavaioFile;

public class IncomingFile {

    String fileName;
    InputStream inputStream;
    long size;
    String contentType;
    File localFile;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public synchronized File saveToLocalFile()
            throws IOException {
        if (localFile == null) {
            if (inputStream == null)
                throw new IllegalUsageException("No input stream to save.");
            localFile = File.createTempFile("incoming-", ".tmp");
            InputStreamSource source = new InputStreamSource(inputStream);
            new JavaioFile(localFile).forWrite().writeBytes(source);
            inputStream = null;
        }
        return localFile;
    }

}
