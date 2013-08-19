package com.bee32.sem.file.entity;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.bee32.plover.ox1.digest.MD5Entity;
import com.bee32.sem.file.io.LocalStorage;
import com.bee32.sem.file.util.ImageBlob;

/**
 * 文件数据
 *
 * <p lang="en">
 * File Blob
 */
@Entity
@AttributeOverrides({//
/*    */@AttributeOverride(name = "label", column = @Column(length = 100)) })
public class FileBlob
        extends MD5Entity {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(FileBlob.class);

    public static final int ENCODING_LENGTH = 16;
    public static final int CONTENT_TYPE_LENGTH = 40;

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

    @Override
    public void populate(Object source) {
        if (source instanceof FileBlob)
            _populate((FileBlob) source);
        else
            super.populate(source);
    }

    protected void _populate(FileBlob o) {
        super._populate(o);
        length = o.length;
        header = o.header;
        thumbnail = o.thumbnail;
        preview = o.preview;
        encoding = o.encoding;
        contentType = o.contentType;
        refCount = o.refCount;
        usage = o.usage;
    }

    /**
     * 文件长度
     *
     * 文件长度 （字节）。
     *
     * <p lang="en">
     * File length in bytes
     */
    @Column(nullable = false)
    public long getLength() {
        return length;
    }

    void setLength(long length) {
        this.length = length;
    }

    /**
     * 文件头
     *
     * 文件的头部数据，可用作预览。
     *
     * 这里存放的文件头不会超过 {@link #HEADER_SIZE}。 当文件比较小时，可以直接从文件头获取整个文件内容。
     *
     *
     * <p lang="en">
     * File Header
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

    /**
     * 缩略图
     *
     * <p lang="en">
     * Thumbnail Image
     */
    @Transient
    public ImageBlob getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ImageBlob thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * 预览
     *
     * <p lang="en">
     * Preview
     */
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

    static LocalStorage container = LocalStorage.INSTANCE;

    transient IFile temporaryFile;

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
        try {
            return file.toSource().newInputStream();
        } catch (IOException e) {
            String errorMessage = "Failed to open " + file.getPath() + ": " + e.getMessage();
            byte[] error = errorMessage.getBytes();
            return new ByteArrayInputStream(error);
        }
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
     * 文本编码
     *
     * 用于为文本文件指定字符编码。
     *
     * <p lang="en">
     * File Encoding
     */
    @Column(length = ENCODING_LENGTH)
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
     * 原始内容类型
     *
     * 文件上传时声明的内容类型。
     *
     * <p lang="en">
     * Orig. Content Type
     */
    @Column(length = CONTENT_TYPE_LENGTH)
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * 引用计数
     *
     * 文件被引用的次数。当引用计数为0时，文件占用的存储空间可被清除。
     */
    @Redundant
    @Column(nullable = false)
    public int getRefCount() {
        return refCount;
    }

    void setRefCount(int refCount) {
        this.refCount = refCount;
    }

    /** Verify if the file with same digest was already committed */
    static boolean verifyRecommit = false;

    /**
     * Commit a temporary file to form a FileBlob.
     *
     * @param _incomingFile
     *            Temporary file at local fs to commit.
     * @param removable
     *            May move or delete the original local file after succeeded to commit.
     * @throws IOException
     */
    public static FileBlob commit(File _incomingFile, boolean removable)
            throws IOException {
        IFile incomingFile = new JavaioFile(_incomingFile);
        long incomingLength = incomingFile.length();
        IStreamInputSource source = incomingFile.toSource();
        FileBlob fileBlob = new FileBlob();

        String digest;
        {
            fileBlob.setTemporaryFile(incomingFile);
            digest = fileBlob.getOrComputeDigest();
            fileBlob.setTemporaryFile(null);
        }

        IFile uniFile = fileBlob.resolve(); // Maybe CDN resource in future.
        if (uniFile.exists() == Boolean.TRUE) {
            if (verifyRecommit) {
                byte[] tmp = incomingFile.forRead().readBinaryContents();
                byte[] uni = uniFile.forRead().readBinaryContents();
                if (!Arrays.equals(tmp, uni))
                    throw new IllegalStateException("Maybe digest collision?");
            }
        } else {
            logger.debug("Committing " + digest + ": " + incomingFile + " -> " + uniFile.getParentFile());

            // file.setCreateParentsMode(true);
            new File(uniFile.getParentFile().getPath().toString()).mkdirs();

            boolean needFullCopy = true;
            if (removable //
                    // && (incomingFile instanceof JavaioFile) //
                    && (uniFile instanceof JavaioFile)) {
                JavaioFile uniFileLocal = (JavaioFile) uniFile;
                String localPath = uniFileLocal.getPath().getLocalPath();
                File localFile = new File(localPath);
                if (_incomingFile.renameTo(localFile))
                    needFullCopy = false;
            }
            if (needFullCopy) {
                IStreamOutputTarget uniTarget = uniFile.toTarget();
                // Always create the file even if it's very small.
                uniTarget.forWrite().writeBytes(source);
            }
        }

        if (uniFile.length() != incomingLength)
            throw new IllegalStateException(String.format(
                    "File with same digest was already there but with different length: exist=%d, incoming=%d",
                    uniFile.length(), incomingLength));

        fileBlob.setLength(uniFile.length());
        fileBlob.setHeader(readHeader(uniFile));

        if (removable && incomingFile.exists() == Boolean.TRUE) {
            logger.debug("Delete the committed file: " + incomingFile);
            incomingFile.delete();
        }
        return fileBlob;
    }

    static byte[] readHeader(IFile file)
            throws IOException {
        int headerMin = (int) Math.min(HEADER_SIZE, file.length());
        byte[] header = file.forRead().readBytes(headerMin);
        return header;
    }

}
