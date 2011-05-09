package com.bee32.sem.people.entity;

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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.ext.xp.EntityExt;
import com.bee32.sem.people.Gender;

/**
 * 自然人或法人。
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
@DiscriminatorValue("-")
public class Person
        extends EntityExt<Integer, PersonXP> {

    private static final long serialVersionUID = 1L;

    User owner;

    Set<PersonTag> tags;

    String name;
    String fullName;
    String nickName;

    Gender sex;

    Date birthday;

    String interests;

    String censusRegister;
    String sidType;
    String sid;

    List<PersonContact> contacts;
    List<PersonLog> logs;

    @OneToMany
    @Override
    protected List<PersonXP> getXPool() {
        return pool();
    }

    @Override
    protected void setXPool(List<PersonXP> xPool) {
        pool(xPool);
    }

    /**
     * 记录的持有者
     */
    @ManyToOne
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @ManyToMany
    @JoinTable(name = "PersonTags", //
    /*            */joinColumns = @JoinColumn(name = "person"), //
    /*            */inverseJoinColumns = @JoinColumn(name = "tag"))
    public Set<PersonTag> getTags() {
        if (tags == null) {
            synchronized (this) {
                if (tags == null) {
                    tags = new HashSet<PersonTag>();
                }
            }
        }
        return tags;
    }

    public void setTags(Set<PersonTag> tags) {
        this.tags = tags;
    }

    /**
     * 显示名称
     */
    @Basic(optional = false)
    @Column(length = 30, nullable = false)
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
    @Column(length = 50)
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
    @Column(length = 20)
    public String getNickName() {
        return nickName;
    }

    /**
     * 昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 性别
     */
    @Enumerated(EnumType.STRING)
    public Gender getSex() {
        return sex;
    }

    /**
     * 性别
     */
    public void setSex(Gender sex) {
        this.sex = sex;
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

    /**
     * 兴趣爱好
     */
    @Column(length = 100)
    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    /**
     * 户籍
     */
    @Column(length = 15)
    public String getCensusRegister() {
        return censusRegister;
    }

    /**
     * 户籍
     */
    public void setCensusRegister(String censusRegister) {
        this.censusRegister = censusRegister;
    }

    /**
     * 身份证件类型 (SID = Social ID)
     */
    @Column(length = 12)
    public String getSidType() {
        return sidType;
    }

    /**
     * 身份证件类型 (SID = Social ID)
     */
    public void setSidType(String sidType) {
        this.sidType = sidType;
    }

    /**
     * 身份证件号码 (SID = Social ID)
     */
    @Column(length = 30)
    public String getSid() {
        return sid;
    }

    /**
     * 身份证件号码 (SID = Social ID)
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    @OneToMany(mappedBy = "person")
    @Cascade(CascadeType.ALL)
    public List<PersonContact> getContacts() {
        return contacts;
    }

    public void setContacts(List<PersonContact> contacts) {
        this.contacts = contacts;
    }

    @OneToMany(mappedBy = "person")
    @Cascade({ CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<PersonLog> getLogs() {
        return logs;
    }

    public void setLogs(List<PersonLog> logs) {
        this.logs = logs;
    }

}
