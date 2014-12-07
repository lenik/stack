package com.bee32.zebra.oa.cloudfs;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import net.bodz.bas.c.java.io.FilePath;
import net.bodz.bas.std.rfc.mime.ContentType;

import com.bee32.zebra.oa.contact.Party;
import com.tinylily.model.base.CoMomentInterval;
import com.tinylily.model.base.security.User;

public class CloudFile
        extends CoMomentInterval {

    private static final long serialVersionUID = 1L;

    private CloudDir parent;

    private ContentType contentType;
    private String extension;
    private String digest;
    private String header;
    private byte[] icon; // icon
    private byte[] preview; // jpeg

    private final Set<String> tags = new HashSet<String>();
    private User op;
    private Party party;

    public final String getFileName() {
        return getCodeName();
    }

    public final void setFileName(String fileName) {
        if (fileName == null)
            throw new NullPointerException("fileName");
        setCodeName(fileName);
        extension = FilePath.getExtension(fileName, false);
        contentType = ContentType.forExtension(extension);
    }

    public final Date getDate() {
        return getBeginTime();
    }

    public final void setDate(Date date) {
        setBeginTime(date);
    }

    public final Date getExpire() {
        return getEndTime();
    }

    public final void setExpire(Date expire) {
        setEndTime(expire);
    }

    public CloudDir getParent() {
        return parent;
    }

    public void setParent(CloudDir parent) {
        this.parent = parent;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public byte[] getPreview() {
        return preview;
    }

    public void setPreview(byte[] preview) {
        this.preview = preview;
    }

    public User getOp() {
        return op;
    }

    public void setOp(User op) {
        this.op = op;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public Set<String> getTags() {
        return tags;
    }

}
