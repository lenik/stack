package com.bee32.sem.file.dto;

import java.io.Serializable;

public class FileName
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public String base;
    public String extension;

    public FileName() {
    }

    public FileName(String base, String extension) {
        if (base == null)
            throw new NullPointerException("base");
        this.base = base;
        this.extension = extension;
    }

    public static FileName parse(String name) {
        String base, extension;
        int dot = name.lastIndexOf('.');
        if (dot == -1) {
            base = name;
            extension = null;
        } else {
            base = name.substring(0, dot);
            extension = name.substring(dot);
        }
        return new FileName(base, extension);
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getExtensionNoDot() {
        if (extension == null)
            return null;
        if (extension.startsWith("."))
            return extension.substring(1);
        else
            return extension;
    }

    @Override
    public String toString() {
        if (extension == null)
            return base;
        else
            return base + extension;
    }

}