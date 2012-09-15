package com.bee32.plover.ox1.digest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bee32.plover.ox1.color.UIEntitySpec;

@MappedSuperclass
public abstract class DigestEntity
        extends UIEntitySpec<String> {

    private static final long serialVersionUID = 1L;

    /**
     * The default length of ID, this could be overrided by MD5/SHA1 entities.
     */
    public static final int ID_LENGTH = 40;

    String digestEncoded;
    Boolean digestValidated;

    @Override
    public void populate(Object source) {
        if (source instanceof DigestEntity)
            _populate((DigestEntity) source);
        else
            super.populate(source);
    }

    protected void _populate(DigestEntity o) {
        super._populate(o);
        digestEncoded = o.digestEncoded;
        digestValidated = o.digestValidated;
    }

    /**
     * 标识摘要
     *
     * 数据的摘要，亦作为记录的标识符。
     */
    @Id
    @Column(length = ID_LENGTH)
    @Override
    public String getId() {
        try {
            return getOrComputeDigest();
        } catch (IOException e) {
            throw new RuntimeException("Failed to compute the digest: " + e.getMessage(), e);
        }
    }

    @Override
    protected void setId(String id) {
        if (id == null)
            throw new NullPointerException("id");

        // Trust the id.
        if (digestEncoded == null) {
            digestEncoded = id;
            digestValidated = true;
        }

        if (id != digestEncoded) {
            digestEncoded = id;
            digestValidated = null;
        }
    }

    /**
     * Get the file digest string.
     *
     * @throws IllegalStateException
     *             If the file digest has not been computed yet.
     */
    @Transient
    public String getDigest() {
        if (digestEncoded == null)
            throw new IllegalStateException("The file digest has not been computed yet.");
        return digestEncoded;
    }

    /**
     * Cause the digest to be re-computed.
     */
    protected void invalidate() {
        digestEncoded = null;
        digestValidated = null;
    }

    @Transient
    protected String getOrComputeDigest()
            throws IOException {
        if (digestValidated == null) {
            synchronized (this) {
                if (digestValidated == null) {
                    InputStream in = readContent();

                    byte[] digest = digest(in);
                    String digestEncoded = encodeDigest(digest);

                    if (this.digestEncoded == null)
                        digestValidated = true;
                    else
                        digestValidated = this.digestEncoded.equals(digestEncoded);

                    this.digestEncoded = digestEncoded;
                }
            }
        }
        return digestEncoded;
    }

    @Transient
    public boolean isDigestValidated() {
        return digestValidated == Boolean.TRUE;
    }

    /**
     * Read the content of this entity as input stream.
     *
     * @see #toInputStream(String)
     */
    protected abstract InputStream readContent()
            throws IOException;

    static Charset UTF_8 = Charset.forName("utf-8");

    protected static InputStream toInputStream(String content) {
        byte[] bytes = content.getBytes(UTF_8);
        return new ByteArrayInputStream(bytes);
    }

    /**
     * Create a new digest instance.
     *
     * @throws UnsupportedOperationException
     *             If the underlying digest service isn't provided.
     */
    protected abstract MessageDigest newDigest();

    protected byte[] digest(InputStream in)
            throws IOException {
        if (in == null)
            throw new NullPointerException("in");

        MessageDigest digest = newDigest();

        byte[] block = new byte[4096];
        int cb;
        while ((cb = in.read(block, 0, block.length)) != -1) {
            digest.update(block, 0, cb);
        }

        return digest.digest();
    }

    static final char[] tab = "0123456789abcdef".toCharArray();

    protected String encodeDigest(byte[] digest) {
        StringBuilder sb = new StringBuilder(100);
        for (int i = 0; i < digest.length; i++) {
            byte byt = digest[i];
            char low = tab[byt & 0x0f];
            char high = tab[(byt >>> 4) & 0x0f];
            sb.append(high);
            sb.append(low);
        }
        return sb.toString();
    }

}
