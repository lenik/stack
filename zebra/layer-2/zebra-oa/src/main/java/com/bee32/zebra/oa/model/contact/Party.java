package com.bee32.zebra.oa.model.contact;

import java.sql.Date;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;

import net.bodz.bas.c.java.util.TimeZones;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public final String getFullName() {
        return getLabel();
    }

    public final void setFullName(String fullName) {
        setLabel(fullName);
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        if (locale == null)
            throw new NullPointerException("locale");
        this.locale = locale;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        if (timeZone == null)
            throw new NullPointerException("timeZone");
        this.timeZone = timeZone;
    }

    public boolean isCustomer() {
        return customer;
    }

    public void setCustomer(boolean customer) {
        this.customer = customer;
    }

    public boolean isSupplier() {
        return supplier;
    }

    public void setSupplier(boolean supplier) {
        this.supplier = supplier;
    }

    public Set<String> getTags() {
        return tags;
    }

    /**
     * 兴趣方向
     * 
     * 个人的兴趣爱好或公司的主营业务。
     * 
     * Person:兴趣爱好 Org:主营业务
     */
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public void setContactId(int contactId) {
        (this.contact = new Contact()).setId(contactId);
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

}
