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

import com.bee32.plover.arch.bean.IPropertyAccessor;
import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.base.tx.TxEntity;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.builtin.ISingleVerifierWithNumber;
import com.bee32.sem.process.verify.builtin.SingleVerifierWithNumberSupport;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.MCValue;

/**
 * 会计凭证条目
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
@DiscriminatorValue("-")
@SequenceGenerator(name = "idgen", sequenceName = "account_ticket_item_seq", allocationSize = 1)
public class AccountTicketItem
        extends TxEntity
        implements IVerifiable<ISingleVerifierWithNumber>, DecimalConfig {

    private static final long serialVersionUID = 1L;

    public static final int TITLE_LENGTH = 30;
    public static final int TEXT_LENGTH = 3000;

    AccountTicket ticket;
    int index;
    AccountSubject subject;
    Party party;
    boolean debitSide;
    MCValue value = new MCValue();
    BigDecimal nativeValue;

    AccountSnapshotItem snapshotItemRef;

    public AccountTicketItem() {
        setVerifyContext(new SingleVerifierWithNumberSupport());
        setDate(new Date());
    }

    @Override
    public void populate(Object source) {
        super.populate(source);
        if (source instanceof AccountSnapshotItem) {
            AccountSnapshotItem si = (AccountSnapshotItem) source;
            setSubject(si.getSubject());
            setParty(si.getParty());
            setValue(si.getValue());
            snapshotItemRef = si;
        }
    }

    @Transient
    public Date getDate() {
        return getBeginTime();
    }

    public void setDate(Date date) {
        setBeginTime(date);
        setEndTime(date);
    }

    @ManyToOne
    public AccountTicket getTicket() {
        return ticket;
    }

    public void setTicket(AccountTicket ticket) {
        this.ticket = ticket;
    }

    /**
     * 单据内部的序号
     */
    @Column(nullable = false)
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * 借方或贷方的一级科目
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
     * 借方或贷方对应的二级科目为客户或供应商或个人
     */
    @ManyToOne
    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    /**
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
     * 金额
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

    public static final IPropertyAccessor<BigDecimal> nativeValueProperty = _property_(//
            AccountTicketItem.class, "nativeValue");

    SingleVerifierWithNumberSupport verifyContext;

    @Embedded
    @Override
    public SingleVerifierWithNumberSupport getVerifyContext() {
        return verifyContext;
    }

    public void setVerifyContext(SingleVerifierWithNumberSupport singleVerifierWithNumberSupport) {
        this.verifyContext = singleVerifierWithNumberSupport;
        singleVerifierWithNumberSupport.bind(this, nativeValueProperty, "金额");
    }

}
