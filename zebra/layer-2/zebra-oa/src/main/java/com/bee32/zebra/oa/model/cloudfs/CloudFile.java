package com.bee32.zebra.oa.model.cloudfs;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import net.bodz.bas.c.java.io.FilePath;
import net.bodz.bas.std.rfc.mime.ContentType;

import com.bee32.zebra.oa.model.contact.Party;
import com.tinylily.model.base.CoMomentInterval;
import com.tinylily.model.base.security.User;

public class CloudFile
        extends CoMomentInterval {

    private static final long serialVersionUID = 1L;

    private CloudDir parent;

    private final Set<String> tags = new HashSet<String>();

    private String digest;
    private String extension;

    private ContentType contentType;
    private String header;
    private byte[] icon; // icon
    private byte[] preview; // jpeg

    private User manager;
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

    public Set<String> getTags() {
        return tags;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getExtension() {
        return extension;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public String getHeader() {
        return header;
    }

    public byte[] getIcon() {
        return icon;
    }

    public byte[] getPreview() {
        return preview;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

}
