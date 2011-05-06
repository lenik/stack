package com.bee32.sem.file.entity;

import java.awt.Image;
import java.beans.Transient;
import java.io.IOException;
import java.io.InputStream;

import javax.free.IFile;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;

import com.bee32.plover.orm.ext.dict.MD5NameDict;

@Entity
@AttributeOverrides({//
/*    */@AttributeOverride(name = "label", column = @Column(length = 100)) })
public class FileBlob
        extends MD5NameDict {

    private static final long serialVersionUID = 1L;

    Image icon;
    IFile file;

    @Transient
    public String getFileName() {
        return getLabel();
    }

    public void setFileName(String fileName) {
        setLabel(fileName);
    }

    @Override
    protected InputStream readContent()
            throws IOException {
        return null;
    }

    @Transient
    public IFile getFile() {
        return file;
    }

    public void setFile(IFile file) {
        this.file = file;
    }

}
