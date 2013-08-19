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

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.And;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.plover.ox1.color.Green;
import com.bee32.plover.ox1.xp.EntityExt;

/**
 * 涉众/参与方
 *
 * 人员或企业组织
 *
 * 自然人或公司的关键信息。
 */
@Entity
@Green
// @DefaultPermission(Permission.R_X)
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
    PartySidType sidType = predefined(PartySidTypes.class).IDENTITYCARD;
    String sid;

    boolean employee;
    boolean customer = true;
    boolean supplier;
    boolean competitor;
    boolean other;

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

    protected Party(String label) {
        setLabel(label);
    }

    @Override
    public void populate(Object source) {
        if (source instanceof Party)
            _populate((Party) source);
        else
            super.populate(source);
    }

    protected void _populate(Party o) {
        super._populate(o);
        fullName = o.fullName;
        sidType = o.sidType;
        sid = o.sid;
        employee = o.employee;
        customer = o.customer;
        supplier = o.supplier;
        competitor = o.competitor;
        other = o.other;
        birthday = o.birthday;
        interests = o.interests;
        bank = o.bank;
        bankAccount = o.bankAccount;
        memo = o.memo;
        tags = new HashSet<PartyTagname>(o.tags);
        contacts = CopyUtils.copyList(o.contacts);
        records = CopyUtils.copyList(o.records);
    }

    @Override
    public void retarget(Object o) {
        super.retarget(o);
        _retarget((Party) o);
    }

    private void _retarget(Party o) {
        // _retargetMerge(tags, o.tags);
        _retargetMerge(contacts, o.contacts);
        _retargetMerge(records, o.records);
    }

    /**
     * 名称
     *
     * 个人称呼或企业名称。
     */
    @Deprecated
    @Transient
    public String getName() {
        return label;
    }

    @Deprecated
    public void setName(String name) {
        if (name == null)
            name = "";
        setLabel(name);
    }

    /**
     * 全名
     *
     * 个人姓名全名或企业的全程。
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
        if (label != null && !label.isEmpty())
            return label;
        return name;
    }

    /**
     * 证件类型
     *
     * 个人证件类型或企业证件类型。
     *
     * (SID = Social ID)
     */
    @NaturalId(mutable = true)
    @ManyToOne
    public PartySidType getSidType() {
        return sidType;
    }

    public void setSidType(PartySidType sidType) {
        this.sidType = sidType;
    }

    /**
     * 证件号码
     *
     * 个人证件的号码或企业注册证件号码。
     *
     * (SID = Social ID)
     */
    @NaturalId(mutable = true)
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

    /**
     * 雇员标志
     *
     * （仅用于个人）表示是否为雇员。
     */
    @DefaultValue("false")
    @Column(nullable = false)
    public boolean isEmployee() {
        return employee;
    }

    public void setEmployee(boolean employee) {
        this.employee = employee;
    }

    /**
     * 客户标志
     *
     * 表示是否为自然人客户或公司客户。
     */
    @DefaultValue("false")
    @Column(nullable = false)
    public boolean isCustomer() {
        return customer;
    }

    public void setCustomer(boolean customer) {
        this.customer = customer;
    }

    /**
     * 供应商标志
     *
     * 表示是否为自然人供应商或公司供应商。
     */
    @DefaultValue("false")
    @Column(nullable = false)
    public boolean isSupplier() {
        return supplier;
    }

    public void setSupplier(boolean supplier) {
        this.supplier = supplier;
    }

    /**
     * 竞争对手标志
     *
     * 表示是否为竞争对手。
     */
    @DefaultValue("false")
    @Column(nullable = false)
    public boolean isCompetitor() {
        return competitor;
    }

    public void setCompetitor(boolean competitor) {
        this.competitor = competitor;
    }

    /**
     * 其他
     *
     * 属性不为雇员，客户，供应商，竞争对手时，可以设置为其他
     */
    @DefaultValue("false")
    @Column(nullable = false)
    public boolean isOther() {
        return other;
    }

    public void setOther(boolean other) {
        this.other = other;
    }

    /**
     * 出生日期
     *
     * 个人的出生日期或组织机构的注册日期。
     */
    @Temporal(TemporalType.DATE)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 兴趣方向
     *
     * 个人的兴趣爱好或公司的主营业务。
     *
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
     *
     * 个人或公司的开户行名称。
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
     *
     * 个人或公司的开户行帐号。
     */
    @Column(length = BANK_ACCOUNT_LENGTH)
    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    /**
     * 备注
     *
     * 更多的备注信息。
     */
    @Column(length = MEMO_LENGTH)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * 标签集
     *
     * 用于分类的标签集合。
     */
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

    /**
     * 联系方式
     *
     * 相关的联系方式列表。
     */
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

    /**
     * 档案记录
     *
     * 在本公司内部维护的人员或组织的重要信息档案记录。
     */
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
        if (sid == null)
            return super.naturalId();
        else
            return new IdComposite(//
                    naturalIdOpt(getSidType()), //
                    sid);
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        if (sid == null)
            return super.selector(prefix);
        return And.of(//
                new Equals(prefix + "sidType", sidType), //
                new Equals(prefix + "sid", sid));
    }

    @Override
    protected void populateKeywords(Collection<String> keywords) {
        keywords.add(getLabel());
        keywords.add(getFullName());
    }

}
