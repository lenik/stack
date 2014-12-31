package com.bee32.zebra.oa.contact;

import java.sql.Date;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;

import net.bodz.bas.c.java.util.TimeZones;
import net.bodz.bas.repr.form.meta.TextInput;
import net.bodz.bas.repr.form.meta.OfGroup;

import com.bee32.zebra.oa.OaGroups;
import com.tinylily.model.base.CoEntity;

public abstract class Party
        extends CoEntity {

    private static final long serialVersionUID = 1L;

    public static final int N_BANK = 50;
    public static final int N_ACCOUNT = 30;
    public static final int N_SUBJECT = 200;
    public static final int N_COMMENT = 200;

    private int id;
    private Date birthday;
    private Locale locale = Locale.SIMPLIFIED_CHINESE;
    private TimeZone timeZone = TimeZones.TZ_SHANGHAI;

    private boolean customer;
    private boolean supplier;
    private final Set<String> tags = new TreeSet<>();

    private String subject;
    private String comment;
    private Contact contact;

    private String bank;
    private String account;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * 生日
     */
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 区域
     */
    @OfGroup(OaGroups.Setting.class)
    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        if (locale == null)
            throw new NullPointerException("locale");
        this.locale = locale;
    }

    /**
     * 语言代码
     */
    @OfGroup(OaGroups.Setting.class)
    public String getLangTag() {
        return locale.toLanguageTag();
    }

    public void setLangTag(String langTag) {
        if (langTag == null)
            throw new NullPointerException("langTag");
        this.locale = Locale.forLanguageTag(langTag);
    }

    /**
     * 时区
     */
    @OfGroup(OaGroups.Setting.class)
    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        if (timeZone == null)
            throw new NullPointerException("timeZone");
        this.timeZone = timeZone;
    }

    /**
     * 时区ID
     */
    @OfGroup(OaGroups.Setting.class)
    public String getTimeZoneId() {
        return timeZone.getID();
    }

    public void setTimeZoneId(String timeZoneId) {
        if (timeZoneId == null)
            throw new NullPointerException("timeZoneId");
        timeZone = TimeZone.getTimeZone(timeZoneId);
    }

    /**
     * 客户标记
     */
    @OfGroup(OaGroups.Classification.class)
    public boolean isCustomer() {
        return customer;
    }

    public void setCustomer(boolean customer) {
        this.customer = customer;
    }

    /**
     * 供应商标记
     */
    @OfGroup(OaGroups.Classification.class)
    public boolean isSupplier() {
        return supplier;
    }

    public void setSupplier(boolean supplier) {
        this.supplier = supplier;
    }

    /**
     * 标签集
     */
    @OfGroup(OaGroups.Classification.class)
    public Set<String> getTags() {
        return tags;
    }

    /**
     * 兴趣方向
     * 
     * 个人的兴趣爱好或公司的主营业务。
     */
    @TextInput(maxLength = N_SUBJECT)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * 注释
     */
    @TextInput(maxLength = N_COMMENT)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 联系方式
     */
    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     * 银行
     * 
     * @placeholder 输入银行名称...
     */
    @TextInput(maxLength = N_BANK)
    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     * 帐号
     * 
     * @placeholder 输入银行帐号...
     */
    @TextInput(maxLength = N_ACCOUNT)
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 由一系列单字符描述的分类特征。
     * 
     * @label Characters
     * @label.zh.cn 特征字
     */
    public String getTypeChars() {
        StringBuilder sb = new StringBuilder();
        if (isCustomer())
            sb.append("客");
        if (isSupplier())
            sb.append("供");
        return sb.toString();
    }

}
