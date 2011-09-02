package com.bee32.sem.asset.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.sem.world.com.Bank;

@Entity
public class BankAccount
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    public static final int ACCOUNT_ID_LENGTH = 30;

    Bank bank;
    String accountId;

    @NaturalId
    @ManyToOne(optional = false)
    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        if (bank == null)
            throw new NullPointerException("bank");
        this.bank = bank;
    }

    @NaturalId
    @Column(length = ACCOUNT_ID_LENGTH, nullable = false)
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        if (accountId == null)
            throw new NullPointerException("accountId");
        this.accountId = accountId;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(naturalId(bank), accountId);
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        if (bank == null)
            throw new NullPointerException("bank");
        if (accountId == null)
            throw new NullPointerException("accountId");
        return selectors(//
                selector(prefix + "bank", bank), //
                new Equals(prefix + "accountId", accountId));
    }
}
