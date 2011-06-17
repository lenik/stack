package com.bee32.plover.orm.ext.digest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bee32.plover.orm.entity.EntityBase;
import com.bee32.plover.orm.ext.color.UIEntitySpec;

@MappedSuperclass
public abstract class DigestEntity
        extends UIEntitySpec<String> {

    private static final long serialVersionUID = 1L;

    String digestEncoded;
    Boolean digestValidated;

    @Id
    @Override
    public String getId() {
        return digestEncoded;
    }

    @Override
    protected void setId(String id) {
        if (id == null)
            throw new NullPointerException("id");

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
            char high = tab[byt >>> 4];
            sb.append(high);
            sb.append(low);
        }
        return sb.toString();
    }

    @Override
    protected Boolean naturalEquals(EntityBase<String> other) {
        DigestEntity o = (DigestEntity) other;

        String digest = getDigest();
        String otherDigest = o.getDigest();

        if (digest == null || otherDigest == null)
            return false;

        if (!digest.equals(otherDigest))
            return false;

        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        String digest = getDigest();

        if (digest == null)
            return 0;
        else
            return digest.hashCode();
    }

}
