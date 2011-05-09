package com.bee32.plover.orm.ext.dict;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
@AttributeOverrides({//
/*    */@AttributeOverride(name = "label", column = @Column(length = 50)) })
public abstract class DigestNameDict
        extends NameDict {

    private static final long serialVersionUID = 1L;

    protected String digestEncoded;

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
    }

    protected String getOrComputeDigest()
            throws IOException {
        if (digestEncoded == null) {
            synchronized (this) {
                if (digestEncoded == null) {
                    InputStream in = readContent();
                    byte[] digest = digest(in);
                    digestEncoded = encodeDigest(digest);
                }
            }
        }
        return digestEncoded;
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
            char high = tab[byt >>> 4];
            sb.append(high);
            sb.append(low);
        }
        return sb.toString();
    }

}
