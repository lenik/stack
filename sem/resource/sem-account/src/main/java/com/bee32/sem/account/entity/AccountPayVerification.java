package com.bee32.sem.account.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.sem.process.base.ProcessEntity;
import com.bee32.sem.world.monetary.MCValue;

/**
 * 应付核销
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "account_pay_verification_seq", allocationSize = 1)
public class AccountPayVerification
        extends ProcessEntity {

    private static final long serialVersionUID = 1L;

    Payable payable;
    Paied paied;
    MCValue amount;

    @ManyToOne
    public Payable getPayable() {
        return payable;
    }

    public void setPayable(Payable payable) {
        this.payable = payable;
    }

    @ManyToOne
    public Paied getPaied() {
        return paied;
    }

    public void setPaied(Paied paied) {
        this.paied = paied;
    }

    /**
     * 应付和已付的核销金额
     */
    @Embedded
    @AttributeOverrides({
            // { price_c, price }
            @AttributeOverride(name = "currencyCode", column = @Column(name = "amount_cc")), //
            @AttributeOverride(name = "value", column = @Column(name = "amount")) })
    public MCValue getAmount() {
        return amount;
    }

    public void setAmount(MCValue amount) {
        this.amount = amount;
    }

}
