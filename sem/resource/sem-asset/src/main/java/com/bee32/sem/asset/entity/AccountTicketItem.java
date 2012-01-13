package com.bee32.sem.asset.entity;

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

import com.bee32.sem.base.tx.TxEntity;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.builtin.IJudgeNumber;
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
        implements IVerifiable<ISingleVerifierWithNumber>, IJudgeNumber {

    private static final long serialVersionUID = 1L;

    public static final int TITLE_LENGTH = 30;
    public static final int TEXT_LENGTH = 3000;

    SingleVerifierWithNumberSupport singleVerifierWithNumberSupport = new SingleVerifierWithNumberSupport(this);

    int index;

    AccountSubject subject;
    Party party;

    MCValue value = new MCValue();

    boolean debitSide;
    AccountTicket ticket;

    AccountSnapshotItem snapshotItemRef;

    public AccountTicketItem() {
        setDate(new Date());
    }

    @Transient
    public Date getDate() {
        return getBeginTime();
    }

    public void setDate(Date date) {
        setBeginTime(date);
        setEndTime(date);
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

    @ManyToOne
    public AccountTicket getTicket() {
        return ticket;
    }

    public void setTicket(AccountTicket ticket) {
        this.ticket = ticket;
    }

    @Transient
    public boolean isTransient() {
        return snapshotItemRef != null;
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

    public void setVerifyContext(SingleVerifierWithNumberSupport singleVerifierWithNumberSupport) {
        this.singleVerifierWithNumberSupport = singleVerifierWithNumberSupport;
        singleVerifierWithNumberSupport.bind(this);
    }

    @Embedded
    @Override
    public SingleVerifierWithNumberSupport getVerifyContext() {
        return singleVerifierWithNumberSupport;
    }

    @Transient
    @Override
    public String getNumberDescription() {
        return "金额";
    }

    @Transient
    @Override
    public Number getJudgeNumber() {
        try {
            return value.getNativeValue(getDate());
        } catch (FxrQueryException e) {
            throw new RuntimeException(e);
        }
    }
}
