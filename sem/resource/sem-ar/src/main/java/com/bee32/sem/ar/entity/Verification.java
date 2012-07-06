package com.bee32.sem.ar.entity;

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
 *
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "verification_seq", allocationSize = 1)
public class Verification extends ProcessEntity {

    private static final long serialVersionUID = 1L;

    Receivable receivable;
    Received received;
    MCValue amount;

    @ManyToOne
    public Receivable getReceivable() {
        return receivable;
    }

    public void setReceivable(Receivable receivable) {
        this.receivable = receivable;
    }

    @ManyToOne
    public Received getReceived() {
        return received;
    }

    public void setReceived(Received received) {
        this.received = received;
    }

    /**
     * 应收和已收的核销金额
     * @return
     */
    @Embedded
    @AttributeOverrides({
            // { price_c, price }
            @AttributeOverride(name = "currencyCode", column = @Column(name = "price_cc")), //
            @AttributeOverride(name = "value", column = @Column(name = "price")) })
    public MCValue getAmount() {
        return amount;
    }

    public void setAmount(MCValue amount) {
        this.amount = amount;
    }

}
