package com.bee32.zebra.oa.file;

import java.util.Date;

import net.bodz.bas.repr.form.meta.OfGroup;

import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;
import com.tinylily.model.base.IMomentInterval;
import com.tinylily.model.base.security.User;
import com.tinylily.model.mx.base.CoMessage;

public class FileInfo
        extends CoMessage
        implements IMomentInterval {

    private static final long serialVersionUID = 1L;

    private String path;
    private long size;
    private String sha1;
    private String type;
    private String encoding;

    private Organization org;
    private Person person;
    private Date activeDate;
    private Date expireDate;

    private int downloads;
    private Double value;

    /**
     * 文件名
     */
    public final String getFileName() {
        return getCodeName();
    }

    public final void setFileName(String fileName) {
        if (fileName == null)
            throw new NullPointerException("fileName");
        setCodeName(fileName);
    }

    /**
     * 路径
     */
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 大小
     */
    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    /**
     * SHA-1摘要
     */
    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    /**
     * 类型
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 字符编码
     */
    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * 公司
     */
    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    /**
     * 联系人
     */
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * 下载次数
     */
    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    /**
     * 价值
     */
    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    /**
     * 生效时间
     */
    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    /**
     * 过期时间
     */
    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    /** ⇱ Implementation Of {@link IMomentInterval }. */
    /* _____________________________ */static section.iface __MOMENT_INTERVAL__;

    @Override
    public final Date getBeginTime() {
        return getActiveDate();
    }

    @Override
    public final void setBeginTime(Date beginTime) {
        setActiveDate(beginTime);
    }

    @Override
    public final Date getEndTime() {
        return getExpireDate();
    }

    @Override
    public final void setEndTime(Date endTime) {
        setExpireDate(endTime);
    }

    /** ⇱ Implementation Of {@link ICoMessage}. */
    /* _____________________________ */static section.iface __MESSAGE__;

    /**
     * 经办人
     */
    @Override
    @OfGroup(CoMessage.class)
    public User getOp() {
        return super.getOp();
    }

}
