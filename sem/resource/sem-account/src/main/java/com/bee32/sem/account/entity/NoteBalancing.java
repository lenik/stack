package com.bee32.sem.account.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.sem.process.base.ProcessEntity;
import com.bee32.sem.world.monetary.MCValue;

/**
 * 票据三总结算方式的基类:贴现，背书，结算
 *
 * @rewrite 票据结算日期：beginTime
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
@DiscriminatorValue("-")
@SequenceGenerator(name = "idgen", sequenceName = "note_balancing_seq", allocationSize = 1)
public class NoteBalancing
        extends ProcessEntity {

    private static final long serialVersionUID = 1L;

    Note note;

    MCValue amount = new MCValue();
    MCValue interest = new MCValue();
    MCValue cost = new MCValue();

    /**
     * 本票据结算对应的票据
     *
     * @return
     */
    @OneToOne(optional = false)
    @JoinColumn(unique = true)
    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    /**
     * 票据结算金额(贴现净额，背书金额，结算金额)
     *
     * @return
     */
    @Embedded
    @AttributeOverrides({ //
    @AttributeOverride(name = "currencyCode", column = @Column(name = "amount_cc")), //
            @AttributeOverride(name = "value", column = @Column(name = "amount")) })
    public MCValue getAmount() {
        return amount;
    }

    public void setAmount(MCValue amount) {
        this.amount = amount;
    }

    /**
     * 利息
     *
     * @return
     */
    @Embedded
    @AttributeOverrides({ //
    @AttributeOverride(name = "currencyCode", column = @Column(name = "interest_cc")), //
            @AttributeOverride(name = "value", column = @Column(name = "interest")) })
    public MCValue getInterest() {
        return interest;
    }

    public void setInterest(MCValue interest) {
        this.interest = interest;
    }

    /**
     * 费用
     *
     * @return
     */
    @Embedded
    @AttributeOverrides({ //
    @AttributeOverride(name = "currencyCode", column = @Column(name = "cost_cc")), //
            @AttributeOverride(name = "value", column = @Column(name = "cost")) })
    public MCValue getCost() {
        return cost;
    }

    public void setCost(MCValue cost) {
        this.cost = cost;
    }
}
