package com.bee32.sem.asset.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.sem.asset.AccountSide;
import com.bee32.sem.base.tx.TxEntity;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.world.monetary.MCValue;

/**
 * 资金记录
 */
/**
 * @author jack
 *
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "asset_record_seq", allocationSize = 1)
public class AccountDocItem
        extends TxEntity {

    private static final long serialVersionUID = 1L;

    public static final int TITLE_LENGTH = 30;
    public static final int TEXT_LENGTH = 3000;

    AccountTitle accountTitle;
    Party org;
    Party person;

    MCValue value = new MCValue();

    AccountSide accountSide;

    AccountDoc accountDoc;

    /**
     * 借方或贷方的一级科目
     */
    @ManyToOne(optional = false)
    public AccountTitle getAccountTitle() {
        return accountTitle;
    }

    public void setAccountTitle(AccountTitle accountTitle) {
        if (accountTitle == null)
            throw new NullPointerException("account title");
        this.accountTitle = accountTitle;
    }

    /**
     * 借方或贷方对应的二级科目为客户或供应商
     */
    @ManyToOne
    public Party getOrg() {
        return org;
    }

    public void setOrg(Party org) {
        this.org = org;
    }

    /**
     * 借方或贷方对应的二级科目为个人
     */
    @ManyToOne
    public Party getPerson() {
        return person;
    }

    public void setPerson(Party person) {
        this.person = person;
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
     * 说明本条目属于凭证上的借方还是贷方
     */
    public AccountSide getAccountSide() {
        return accountSide;
    }

    public void setAccountSide(AccountSide accountSide) {
        this.accountSide = accountSide;
    }

    @ManyToOne(optional = false)
    public AccountDoc getAccountDoc() {
        return accountDoc;
    }

    public void setAccountDoc(AccountDoc accountDoc) {
        this.accountDoc = accountDoc;
    }
}
