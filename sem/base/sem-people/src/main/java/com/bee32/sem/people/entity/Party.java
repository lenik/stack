package com.bee32.sem.people.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DefaultValue;
import org.hibernate.annotations.NaturalId;

import com.bee32.icsf.access.DefaultPermission;
import com.bee32.icsf.access.Permission;
import com.bee32.plover.arch.util.DummyId;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.color.Green;
import com.bee32.plover.ox1.xp.EntityExt;

/**
 * 自然人或法人。
 */
@Entity
@Green
@DefaultPermission(Permission.R_X)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
@DiscriminatorValue("-")
@SequenceGenerator(name = "idgen", sequenceName = "party_seq", allocationSize = 1)
public abstract class Party
        extends EntityExt<Integer, PartyXP> {

    private static final long serialVersionUID = 1L;

    public static final int FULLNAME_LENGTH = 50;
    public static final int SID_LENGTH = 30;
    public static final int XID_LENGTH = 40;
    public static final int INTERESTS_LENGTH = 200;
    public static final int BANK_LENGTH = 100;
    public static final int BANK_ACCOUNT_LENGTH = 50;
    public static final int MEMO_LENGTH = 1000;

    String fullName;
    PartySidType sidType = PartySidType.IDENTITYCARD;
    String sid;

    boolean employee;
    boolean customer = true;
    boolean supplier;

    Date birthday;
    String interests;

    String bank;
    String bankAccount;

    String memo;

    Set<PartyTagname> tags = new HashSet<PartyTagname>();
    List<Contact> contacts = new ArrayList<Contact>();
    List<PartyRecord> records = new ArrayList<PartyRecord>();

    protected Party() {
    }

    protected Party(String name) {
        this.name = name;
    }

    /**
     * 名称
     */
    @Transient
    public String getName() {
        return label;
    }

    public void setName(String name) {
        if (name == null)
            name = "";
        setLabel(name);
    }

    /**
     * 全名
     */
    @Column(length = FULLNAME_LENGTH)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 显示名称
     */
    @Transient
    public String getDisplayName() {
        if (fullName != null && !fullName.isEmpty())
            return fullName;
        return name;
    }

    /**
     * 证件类型 (SID = Social ID)
     */
    @ManyToOne
    public PartySidType getSidType() {
        return sidType;
    }

    public void setSidType(PartySidType sidType) {
        this.sidType = sidType;
    }

    /**
     * 证件号码 (SID = Social ID)
     */
    @Column(length = SID_LENGTH)
    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        if (sid != null) {
            sid = sid.trim();
            if (sid.isEmpty())
                sid = null;
        }
        this.sid = sid;
    }

    @NaturalId(mutable = true)
    @Column(length = XID_LENGTH)
    String getXid() {
        if (sidType == null || sid == null || sid.isEmpty())
            return null;
        return sidType.getId() + ":" + sid;
    }

    void setXid(String xid) {
        // work on the fly.
    }

    @DefaultValue("false")
    @Column(nullable = false)
    public boolean isEmployee() {
        return employee;
    }

    public void setEmployee(boolean employee) {
        this.employee = employee;
    }

    @DefaultValue("false")
    @Column(nullable = false)
    public boolean isCustomer() {
        return customer;
    }

    public void setCustomer(boolean customer) {
        this.customer = customer;
    }

    @DefaultValue("false")
    @Column(nullable = false)
    public boolean isSupplier() {
        return supplier;
    }

    public void setSupplier(boolean supplier) {
        this.supplier = supplier;
    }

    /**
     * 出生日期
     */
    @Temporal(TemporalType.DATE)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * Person:兴趣爱好 Org:主营业务
     */
    @Column(length = INTERESTS_LENGTH)
    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    /**
     * 银行名称
     */
    @Column(length = BANK_LENGTH)
    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     * 银行帐号
     */
    @Column(length = BANK_ACCOUNT_LENGTH)
    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Column(length = MEMO_LENGTH)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @ManyToMany
    // (fetch = FetchType.EAGER)
    @JoinTable(name = "PartyTags", //
    /*            */joinColumns = @JoinColumn(name = "party"), //
    /*            */inverseJoinColumns = @JoinColumn(name = "tag"))
    public Set<PartyTagname> getTags() {
        return tags;
    }

    public void setTags(Set<PartyTagname> tags) {
        if (tags == null)
            throw new NullPointerException("tags");
        this.tags = tags;
    }

    @OneToMany(mappedBy = "party", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        if (contacts == null)
            throw new NullPointerException("contacts");
        this.contacts = contacts;
    }

    @OneToMany(mappedBy = "party", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<PartyRecord> getRecords() {
        return records;
    }

    public void setRecords(List<PartyRecord> records) {
        if (records == null)
            throw new NullPointerException("records");
        this.records = records;
    }

    @Override
    protected Serializable naturalId() {
        String xid = getXid();
        if (xid == null)
            return new DummyId(this);
        else
            return xid;
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        String xid = getXid();
        if (xid == null)
            return null;
        return new Equals(prefix + "xid", xid);
    }

    @Override
    protected void populateKeywords(Collection<String> keywords) {
        keywords.add(getLabel());
        keywords.add(getFullName());
    }

}
