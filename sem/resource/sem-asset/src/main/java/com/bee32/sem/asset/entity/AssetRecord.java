package com.bee32.sem.asset.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.principal.User;
import com.bee32.sem.base.tx.TxEntity;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.world.monetary.MCValue;

/**
 * 资金记录
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "asset_record_seq", allocationSize = 1)
public class AssetRecord
        extends TxEntity {

    private static final long serialVersionUID = 1L;

    public static final int TITLE_LENGTH = 30;
    public static final int TEXT_LENGTH = 3000;

    AssetSubject subject; // =AssetSubject.XXX;
    Party party1;
    User user1;
    Party party2;
    User user2;
    MCValue value = new MCValue();
    PayMethod payMethod = PayMethod.PAY_MONEY;
    BankAccount bankAccount;
    String title;
    String text;

    /**
     * 科目
     */
    @ManyToOne(optional = false)
    public AssetSubject getSubject() {
        return subject;
    }

    public void setSubject(AssetSubject subject) {
        if (subject == null)
            throw new NullPointerException("subject");
        this.subject = subject;
    }

    /**
     * 甲方（必填）
     *
     * 是本系统资金结算的一方。
     */
    @ManyToOne(optional = false)
    public Party getParty1() {
        return party1;
    }

    public void setParty1(Party party1) {
        if (party1 == null)
            throw new NullPointerException("party1");
        this.party1 = party1;
    }

    /**
     * 甲方对应用户（如果甲方为本系统的用户）
     */
    @ManyToOne
    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    /**
     * 乙方
     *
     * 在本系统内乙方仅作记录，不参与资金结算。
     */
    @ManyToOne
    public Party getParty2() {
        return party2;
    }

    public void setParty2(Party party2) {
        this.party2 = party2;
    }

    /**
     * 乙方对应的用户（如果乙方为本系统的用户）
     */
    @ManyToOne
    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
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
     * 付款方式
     */
    @ManyToOne
    public PayMethod getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(PayMethod payMethod) {
        this.payMethod = payMethod;
    }

    /**
     * 银行帐号（如果付款方式为银行转帐等）
     */
    @ManyToOne
    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    /**
     * 打印标题。如“付款单”、“借据”等。
     *
     * 在实际使用中，如果 title 为空（null 或空字符串），则应使用科目名称作为打印标题。
     */
    @Column(length = TITLE_LENGTH)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 打印正文。
     */
    @Column(length = TEXT_LENGTH)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
