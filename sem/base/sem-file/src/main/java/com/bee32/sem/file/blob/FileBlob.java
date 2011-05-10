package com.bee32.sem.file.blob;

import java.io.IOException;
import java.io.InputStream;

import javax.free.IFile;
import javax.free.IStreamReadPreparation;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.bee32.plover.orm.ext.digest.MD5Entity;
import com.bee32.sem.file.entity.FileStore;
import com.bee32.sem.file.util.ImageBlob;

@Entity
@AttributeOverrides({//
/*    */@AttributeOverride(name = "label", column = @Column(length = 100)) })
public class FileBlob
        extends MD5Entity {

    private static final long serialVersionUID = 1L;

    public static final int HEADER_SIZE = 4000;

    FileStore store;

    String origPath;

    long length;
    byte[] header;

    ImageBlob smallImage;
    ImageBlob mediumImage;

    /**
     * The same as {@link #getLabel()}.
     */
    @Transient
    public String getFileName() {
        return getLabel();
    }

    /**
     * The same as {@link #setLabel(String)}.
     */
    public void setFileName(String fileName) {
        setLabel(fileName);
    }

    @ManyToOne
    public FileStore getStore() {
        return store;
    }

    public void setStore(FileStore store) {
        this.store = store;
    }

    /**
     * Generally, the original path is not used.
     *
     * It's just existed for reference purpose, or for reconstruct the original fs-tree.
     */
    @Column(length = 100)
    public String getOrigPath() {
        return origPath;
    }

    public void setOrigPath(String origPath) {
        this.origPath = origPath;
    }

    @Column(nullable = false)
    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    @Column(length = HEADER_SIZE)
    public byte[] getHeader() {
        return header;
    }

    public void setHeader(byte[] header) {
        this.header = header;
    }

    @Transient
    public ImageBlob getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(ImageBlob smallImage) {
        this.smallImage = smallImage;
    }

    @Transient
    public ImageBlob getMediumImage() {
        return mediumImage;
    }

    public void setMediumImage(ImageBlob mediumImage) {
        this.mediumImage = mediumImage;
    }

    @Override
    protected InputStream readContent()
            throws IOException {
        IFile file = resolve();
        return file.toSource().newInputStream();
    }

    public IFile resolve()
            throws IOException {
        return store.resolve(getDigest());
    }

    public IStreamReadPreparation open()
            throws IOException {
        return resolve().forRead();
    }

}
