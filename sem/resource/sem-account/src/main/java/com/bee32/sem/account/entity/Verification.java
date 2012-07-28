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
 * 核销
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "verification_seq", allocationSize = 1)
public class Verification
        extends ProcessEntity {

    private static final long serialVersionUID = 1L;

    AccountAble accountAble;
    AccountEd accountEd;
    MCValue amount = new MCValue();

    @ManyToOne
    public AccountAble getAccountAble() {
        return accountAble;
    }

    public void setAccountAble(AccountAble accountAble) {
        this.accountAble = accountAble;
    }

    @ManyToOne
    public AccountEd getAccountEd() {
        return accountEd;
    }

    public void setAccountEd(AccountEd accountEd) {
        this.accountEd = accountEd;
    }

    /**
     * 应收和已收的核销金额
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
