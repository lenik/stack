package com.bee32.sem.people.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.persistence.Lob;
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
import com.bee32.plover.orm.ext.color.Green;
import com.bee32.plover.orm.ext.xp.EntityExt;

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
    private static final int NAME_LENGTH = 30;
    private static final int FULLNAME_LENGTH =50;
    private static final int NICKNAME_LENGTH = 20;
    private static final int SID_LENGTH = 30;
    private static final int XID_LENGTH = 40;
    private static final int INTERESTS_LENGTH = 200;

    Set<PartyTagname> tags;

    String name;
    String fullName;
    String nickName;

    PartySidType sidType = PartySidType.IDENTITYCARD;
    String sid;

    Date birthday;

    String interests;

    String memo;

    List<Contact> contacts = new ArrayList<Contact>();
    List<PartyRecord> records = new ArrayList<PartyRecord>();

    protected Party() {
    }

    protected Party(String name) {
        this.name = name;
    }

    @ManyToMany
    @JoinTable(name = "PartyTags", //
    /*            */joinColumns = @JoinColumn(name = "party"), //
    /*            */inverseJoinColumns = @JoinColumn(name = "tag"))
    public Set<PartyTagname> getTags() {
        if (tags == null) {
            synchronized (this) {
                if (tags == null) {
                    tags = new HashSet<PartyTagname>();
                }
            }
        }
        return tags;
    }

    public void setTags(Set<PartyTagname> tags) {
        this.tags = tags;
    }

    /**
     * 显示名称
     */
    @Basic(optional = false)
    @Column(length = NAME_LENGTH, nullable = false)
    public String getName() {
        return name;
    }

    /**
     * 显示名称
     */
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

    /**
     * 全名
     */
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

    /**
     * 昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Transient
    public String getDisplayName() {
        if (fullName != null && !fullName.isEmpty())
            return fullName;
        if (nickName != null && !nickName.isEmpty())
            return nickName;
        return name;
    }

    /**
     * 证件类型 (SID = Social ID)
     */
    @ManyToOne
    public PartySidType getSidType() {
        return sidType;
    }

    /**
     * 证件类型 (SID = Social ID)
     */
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

    /**
     * 证件号码 (SID = Social ID)
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    @NaturalId
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

    /**
     * 出生日期
     */
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

    @Lob
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Transient
    public Integer getAge() {
        if (birthday == null)
            return null;

        Calendar cal = Calendar.getInstance();
        cal.setTime(this.birthday);
        int birthYear = cal.get(Calendar.YEAR);

        cal.setTimeInMillis(System.currentTimeMillis());
        int currentYear = cal.get(Calendar.YEAR);

        return currentYear - birthYear;
    }

    @Transient
    public Integer getBirthdayRemains() {
        if (birthday == null)
            return null;

        Calendar cal = Calendar.getInstance();
        int birthDOY = cal.get(Calendar.DAY_OF_YEAR);

        cal.setTimeInMillis(System.currentTimeMillis());
        int currentDOY = cal.get(Calendar.DAY_OF_YEAR);

        int diff = currentDOY - birthDOY;
        if (diff < 0)
            diff += 365;

        return diff;
    }

    @OneToMany(mappedBy = "party")
    @Cascade({ CascadeType.ALL })
    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        if (contacts == null)
            throw new NullPointerException("contacts");
        this.contacts = contacts;
    }

    @OneToMany(mappedBy = "party")
    @Cascade({ CascadeType.ALL, CascadeType.DELETE_ORPHAN })
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
        return new Equals(prefix + "xid", xid);
    }
}
