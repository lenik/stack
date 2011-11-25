package com.bee32.sem.asset.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.sem.base.tx.TxEntity;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.world.monetary.MCValue;

/**
 * Accounting summary snapshot.
 * <p>
 * beginTime is unused, endTime is for timestamp.
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "account_snapshot_seq", allocationSize = 1)
public class AccountSnapshot
        extends TxEntity {

    private static final long serialVersionUID = 1L;

    AccountSubject subject;
    Party party;
    MCValue value;

    @ManyToOne(optional = false)
    public AccountSubject getSubject() {
        return subject;
    }

    public void setSubject(AccountSubject subject) {
        if (subject == null)
            throw new NullPointerException("subject");
        this.subject = subject;
    }

    @ManyToOne(optional = true)
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

}
