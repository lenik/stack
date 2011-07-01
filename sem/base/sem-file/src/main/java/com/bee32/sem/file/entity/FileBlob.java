package com.bee32.sem.file.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.free.IFile;
import javax.free.InputBytesFile;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.bee32.plover.orm.ext.digest.MD5Entity;
import com.bee32.sem.file.io.LocalFileContainer;
import com.bee32.sem.file.util.ImageBlob;

@Entity
@AttributeOverrides({//
/*    */@AttributeOverride(name = "label", column = @Column(length = 100)) })
public class FileBlob
        extends MD5Entity {

    private static final long serialVersionUID = 1L;

    public static final int HEADER_SIZE = 4000;

    long length;
    byte[] header;

    ImageBlob thumbnail;
    ImageBlob preview;

    // BASE CONTENT MINING.
    String encoding;
    String contentType;

    /**
     * 文件长度 （字节）。
     */
    @Column(nullable = false)
    public long getLength() {
        return length;
    }

    void setLength(long length) {
        this.length = length;
    }

    /**
     * 文件头。
     *
     * 这里存放的文件头不会超过 {@link #HEADER_SIZE}。 当文件比较小时，可以直接从文件头获取整个文件内容。
     *
     * @see #HEADER_SIZE
     * @see #readContent()
     */
    @Column(length = HEADER_SIZE, nullable = false)
    public byte[] getHeader() {
        return header;
    }

    void setHeader(byte[] header) {
        if (header == null)
            throw new NullPointerException("header");
        this.header = header;
    }

    @Transient
    public ImageBlob getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ImageBlob thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Transient
    public ImageBlob getPreview() {
        return preview;
    }

    public void setPreview(ImageBlob preview) {
        this.preview = preview;
    }

    @Override
    public InputStream readContent()
            throws IOException {
        IFile file = resolve();
        return file.toSource().newInputStream();
    }

    public Reader readTextContent()
            throws IOException {
        InputStream in = readContent();
        String encoding = getEncoding();
        if (encoding == null)
            encoding = "utf-8";
        InputStreamReader reader = new InputStreamReader(in, encoding);
        return reader;
    }

    @Transient
    LocalFileContainer container = LocalFileContainer.INSTANCE;

    public IFile resolve()
            throws IOException {
        String digest = getDigest();
        if (length <= HEADER_SIZE) {
            InputBytesFile memoryFile = new InputBytesFile(digest, header);
            return memoryFile;
        } else {
            return container.resolveHash(digest);
        }
    }

    /**
     * 文本编码。
     */
    @Column(length = 16)
    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * 原始内容类型。
     */
    @Column(length = 40)
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

}
