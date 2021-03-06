package com.bee32.sem.asset.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.ox1.c.CEntity;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.people.entity.OrgUnit;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.process.base.ProcessEntity;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.MCValue;

/**
 * 会计凭证明细
 *
 * 会计凭证上的明细科目，和会计科目对应 。
 *
 * <p lang="en">
 * Account Ticket Item
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
@DiscriminatorValue("-")
@SequenceGenerator(name = "idgen", sequenceName = "account_ticket_item_seq", allocationSize = 1)
public class AccountTicketItem
        extends ProcessEntity
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    public static final int TITLE_LENGTH = 30;
    public static final int TEXT_LENGTH = 3000;

    AccountTicket ticket;
    int index;
    AccountSubject subject;
    Party party;
    Person person;
    OrgUnit orgUnit;
    boolean debitSide;
    MCValue value = new MCValue();
    BigDecimal nativeValue;

    AccountSnapshotItem snapshotItemRef;

    public AccountTicketItem() {
        setDate(new Date());
    }

    @Override
    public void populate(Object source) {
        if (source instanceof AccountTicketItem)
            _populate((AccountTicketItem) source);
        else if (source instanceof AccountSnapshotItem)
            _populate((AccountSnapshotItem) source);
        else
            super.populate(source);
    }

    protected void _populate(AccountTicketItem o) {
        super._populate(o);
        ticket = o.ticket;
        index = o.index;
        subject = o.subject;
        party = o.party;
        debitSide = o.debitSide;
        value = o.value;
        nativeValue = o.nativeValue;
    }

    protected void _populate(AccountSnapshotItem o) {
        super._populate(o);
        subject = o.subject;
        party = o.party;
        value = o.value;
        snapshotItemRef = o;
    }

    /**
     * 凭证明细建立时间
     *
     * 凭证明细建立时间。
     *
     * @return
     */
    @Transient
    public Date getDate() {
        return getBeginTime();
    }

    public void setDate(Date date) {
        setBeginTime(date);
        setEndTime(date);
    }

    /**
     * 凭证主控类
     *
     * 注： 所属单据这里命名为 ticket 而不是 parent，以便派生类可以有自己的 parent。
     *
     * @see AccountInitItem
     */
    @ManyToOne(optional = true)
    public AccountTicket getTicket() {
        return ticket;
    }

    public void setTicket(AccountTicket ticket) {
        this.ticket = ticket;
    }

    /**
     * 显示顺序
     *
     * 明细在列表中的顺序。
     */
    @Column(nullable = false)
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * 科目
     *
     * 借方或贷方的科目。
     */
    @ManyToOne(optional = false)
    public AccountSubject getSubject() {
        return subject;
    }

    public void setSubject(AccountSubject subject) {
        if (subject == null)
            throw new NullPointerException("subject");
        this.subject = subject;
    }

    /**
     * 客户，供应商，个人
     *
     * 科目发生额对应的客户或供应商或个人。
     */
    @ManyToOne
    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    /**
     * 经办人
     *
     * 企业内部经办人(业务员，采购员等)。
     *
     * @return
     */
    @ManyToOne
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * 部门
     *
     * 科目发生额对应的内部部门。
     *
     * @return
     */
    @ManyToOne
    public OrgUnit getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(OrgUnit orgUnit) {
        this.orgUnit = orgUnit;
    }

    /**
     * 凭证明细方向
     *
     * 说明本条目属于凭证上的借方还是贷方。
     *
     * @return <code>true</code> 表示借方，<code>false</code> 表示贷方。
     */
    @Column(nullable = false)
    public boolean isDebitSide() {
        return debitSide;
    }

    public void setDebitSide(boolean debitSide) {
        this.debitSide = debitSide;
    }

    /**
     * 科目发生金额
     *
     * 凭证上科目发生的金额。
     */
    @Embedded
    @AttributeOverrides({
            // { price_cc, price }
            @AttributeOverride(name = "currency", column = @Column(name = "value_cc")), //
            @AttributeOverride(name = "value", column = @Column(name = "value")) })
    public MCValue getValue() {
        return value;
    }

    public void setValue(MCValue value) {
        if (value == null)
            throw new NullPointerException("value");
        this.value = value;
        this.nativeValue = null;
    }

    /**
     * 本币金额
     *
     * 【冗余】本地货币表示的价格。
     *
     * @return 本地货币表示的价格，非 <code>null</code>。
     * @throws FxrQueryException
     *             外汇查询异常。
     */
    @Redundant
    @Column(precision = MONEY_ITEM_PRECISION, scale = MONEY_ITEM_SCALE)
    public synchronized BigDecimal getNativeValue()
            throws FxrQueryException {
        if (nativeValue == null) {
            nativeValue = value.getNativeValue(getDate());
        }
        return nativeValue;
    }

    void setNativeValue(BigDecimal nativeValue) {
        this.nativeValue = nativeValue;
    }

    @Transient
    public boolean isTransient() {
        return snapshotItemRef != null;
    }

    /**
     * 数字代表的意义
     *
     * 说明凭证上的数字的的含义。
     *
     */
    @Transient
    @Override
    public String getNumberDescription() {
        return "金额";
    }

    @Override
    protected Number computeJudgeNumber()
            throws FxrQueryException {
        return getNativeValue();
    }

    @Override
    protected CEntity<?> owningEntity() {
        return ticket;
    }

}
