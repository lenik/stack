package com.bee32.sem.people.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
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
import org.hibernate.annotations.NaturalId;

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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
@DiscriminatorValue("-")
@SequenceGenerator(name = "idgen", sequenceName = "party_seq", allocationSize = 1)
public abstract class Party
        extends EntityExt<Integer, PartyXP> {

    private static final long serialVersionUID = 1L;

    public static final int NAME_LENGTH = 30;
    public static final int FULLNAME_LENGTH = 50;
    public static final int NICKNAME_LENGTH = 20;
    public static final int SID_LENGTH = 30;
    public static final int XID_LENGTH = 40;
    public static final int INTERESTS_LENGTH = 200;
    public static final int MEMO_LENGTH = 1000;

    String name;
    String fullName;
    String nickName;
    PartySidType sidType = PartySidType.IDENTITYCARD;
    String sid;

    Date birthday;
    String interests;
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
    @Basic(optional = false)
    @Column(length = NAME_LENGTH, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
     * 昵称
     */
    @Column(length = NICKNAME_LENGTH)
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 显示名称
     */
    @Transient
    public String getDisplayName() {
        if (nickName != null && !nickName.isEmpty())
            return nickName;
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
        keywords.add(name);
        keywords.add(fullName);
        keywords.add(nickName);
    }

}
