package com.bee32.zebra.oa.file;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;
import com.tinylily.model.base.CoMomentInterval;
import com.tinylily.model.base.security.User;

public class FileInfo
        extends CoMomentInterval {

    private static final long serialVersionUID = 1L;

    private String path;
    private long size;
    private String sha1;
    private String type;
    private String encoding;

    private User op;
    private Organization org;
    private Person person;
    private final Set<String> tags = new HashSet<String>();

    private int votes;
    private int favorites;
    private int downloads;
    private Double value;

    public final String getFileName() {
        return getCodeName();
    }

    public final void setFileName(String fileName) {
        if (fileName == null)
            throw new NullPointerException("fileName");
        setCodeName(fileName);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public User getOp() {
        return op;
    }

    public void setOp(User op) {
        this.op = op;
    }

    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public int getFavorites() {
        return favorites;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Set<String> getTags() {
        return tags;
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

}
