package com.bee32.sem.file.entity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.free.IFile;
import javax.free.IStreamInputSource;
import javax.free.IStreamOutputTarget;
import javax.free.InputBytesFile;
import javax.free.JavaioFile;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.orm.ext.digest.MD5Entity;
import com.bee32.sem.file.io.LocalStorage;
import com.bee32.sem.file.util.ImageBlob;

/**
 * 文件数据
 */
@Entity
@AttributeOverrides({//
/*    */@AttributeOverride(name = "label", column = @Column(length = 100)) })
public class FileBlob
        extends MD5Entity {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(FileBlob.class);

    public static final int HEADER_SIZE = 4000;

    long length;
    byte[] header;

    ImageBlob thumbnail;
    ImageBlob preview;

    // BASE CONTENT MINING.
    String encoding;
    String contentType;

    int refCount;
    List<UserFile> usage = new ArrayList<UserFile>();

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
    protected InputStream readContent()
            throws IOException {
        return newInputStream();
    }

    @Transient
    LocalStorage container = LocalStorage.INSTANCE;

    IFile temporaryFile;

    @Transient
    IFile getTemporaryFile() {
        return temporaryFile;
    }

    void setTemporaryFile(IFile temporaryFile) {
        this.temporaryFile = temporaryFile;
    }

    public IFile resolve()
            throws IOException {

        if (temporaryFile != null)
            return temporaryFile;

        String digest = getDigest();
        if (digest == null)
            throw new NullPointerException("digest");

        IFile file;

        boolean headerLoaded = header != null;
        if (headerLoaded && length <= HEADER_SIZE) {
            InputBytesFile memoryFile = new InputBytesFile(digest, header);
            file = memoryFile;

        } else {
            file = container.resolveHash(digest);
        }

        String encoding = getEncoding("utf-8");
        file.setPreferredCharset(encoding);
        return file;
    }

    public InputStream newInputStream()
            throws IOException {
        IFile file = resolve();
        return file.toSource().newInputStream();
    }

    public Reader newReader()
            throws IOException {
        IFile file = resolve();
        return file.toSource().newReader();
    }

    OutputStream newOutputStream()
            throws IOException {
        IFile file = resolve();
        return file.toTarget().newOutputStream();
    }

    Writer newWriter()
            throws IOException {
        IFile file = resolve();
        return file.toTarget().newWriter();
    }

    /**
     * 文本编码。
     */
    @Column(length = 16)
    public String getEncoding() {
        return encoding;
    }

    public String getEncoding(String defaultEncoding) {
        if (encoding == null)
            return defaultEncoding;
        else
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

    @Redundant
    @Column(nullable = false)
    public int getRefCount() {
        return refCount;
    }

    void setRefCount(int refCount) {
        this.refCount = refCount;
    }

    /**
     * Commit a temporary file to form a FileBlob.
     *
     * @param localFile
     *            Local file to commit.
     * @param deleteAfterCommitted
     *            Delete the original local file after succeeded to commit.
     * @throws IOException
     */
    public static FileBlob commit(File localFile, boolean deleteAfterCommitted)
            throws IOException {
        FileBlob fileBlob = new FileBlob();

        IFile temporaryFile = new JavaioFile(localFile);
        fileBlob.setTemporaryFile(temporaryFile);
        String digest = fileBlob.getOrComputeDigest();
        fileBlob.setTemporaryFile(null);

        // Forecast file to be committed.
        IFile file = fileBlob.resolve();
        // file.setCreateParentsMode(true);
        new File(file.getParentFile().getPath().toString()).mkdirs();

        // Prepare streaming.
        IStreamInputSource source = temporaryFile.toSource();
        IStreamOutputTarget target = file.toTarget();

        logger.debug("Committing " + digest + ": " + localFile + " -> " + file.getParentFile());

        int headerMin = HEADER_SIZE;
        if (localFile.length() <= HEADER_SIZE)
            headerMin = (int) localFile.length();

        // Always create the file even if it's very small.
        target.forWrite().writeBytes(source);

        byte[] header = source.forRead().readBytes(headerMin);
        fileBlob.setHeader(header);

        fileBlob.setLength(temporaryFile.length());

        if (!deleteAfterCommitted) {
            logger.debug("Delete the committed file: " + localFile);
            localFile.delete();
        }

        return fileBlob;
    }

}
