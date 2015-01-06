package com.bee32.zebra.oa.file;

import java.util.Date;

import net.bodz.bas.meta.bean.DetailLevel;
import net.bodz.bas.meta.cache.Derived;
import net.bodz.bas.repr.form.NullConvertion;
import net.bodz.bas.repr.form.meta.FormInput;
import net.bodz.bas.repr.form.meta.OfGroup;
import net.bodz.bas.repr.form.meta.StdGroup;
import net.bodz.bas.repr.form.meta.TextInput;

import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;
import com.tinylily.model.base.IMomentInterval;
import com.tinylily.model.base.IdType;
import com.tinylily.model.base.security.User;
import com.tinylily.model.mx.base.CoMessage;

@IdType(Integer.class)
public class FileInfo
        extends CoMessage<Integer> {

    private static final long serialVersionUID = 1L;

    public static final int N_PATH = 200;
    public static final int N_SHA1 = 32;
    public static final int N_TYPE = 100;
    public static final int N_ENCODING = 30;

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
    @FormInput(nullconv = NullConvertion.EMPTY)
    @TextInput(maxLength = N_PATH)
    public final String getFileName() {
        return super.getCodeName();
    }

    public final void setFileName(String fileName) {
        if (fileName == null)
            throw new NullPointerException("fileName");
        super.setCodeName(fileName);
    }

    /**
     * 路径
     */
    @FormInput(nullconv = NullConvertion.EMPTY)
    @TextInput(maxLength = N_PATH)
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
    @OfGroup(StdGroup.Settings.class)
    @FormInput(nullconv = NullConvertion.EMPTY)
    @TextInput(maxLength = N_SHA1)
    @Derived(cached = true)
    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    /**
     * 类型
     */
    @FormInput(nullconv = NullConvertion.EMPTY)
    @TextInput(maxLength = N_TYPE)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 字符编码
     */
    @OfGroup(StdGroup.Settings.class)
    @FormInput(nullconv = NullConvertion.EMPTY)
    @TextInput(maxLength = N_ENCODING)
    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * 公司
     */
    @OfGroup(StdGroup.Schedule.class)
    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    /**
     * 联系人
     */
    @OfGroup(StdGroup.Schedule.class)
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * 下载次数
     */
    @OfGroup(StdGroup.Statistics.class)
    @Derived(cached = true)
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
    @OfGroup(StdGroup.Schedule.class)
    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    /**
     * 过期时间
     */
    @OfGroup(StdGroup.Schedule.class)
    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    /** ⇱ Implementation Of {@link IMomentInterval }. */
    /* _____________________________ */static section.iface __MOMENT_INTERVAL__;

    @DetailLevel(DetailLevel.HIDDEN)
    @Derived
    @Override
    public final Date getBeginDate() {
        return getActiveDate();
    }

    @Override
    public final void setBeginDate(Date beginDate) {
        setActiveDate(beginDate);
    }

    @DetailLevel(DetailLevel.HIDDEN)
    @Derived
    @Override
    public final Date getEndDate() {
        return getExpireDate();
    }

    @Override
    public final void setEndDate(Date endDate) {
        setExpireDate(endDate);
    }

    /** ⇱ Implementation Of {@link ICoMessage}. */
    /* _____________________________ */static section.iface __MESSAGE__;

    /**
     * 经办人
     */
    // @OfGroup(OaGroups.UserInteraction.class)
    @Override
    public User getOp() {
        return super.getOp();
    }

}
