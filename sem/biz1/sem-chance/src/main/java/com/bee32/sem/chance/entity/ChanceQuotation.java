package com.bee32.sem.chance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.sem.world.thing.AbstractItemList;

/**
 * 报价单
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "chance_quotation_seq", allocationSize = 1)
public class ChanceQuotation
        extends AbstractItemList<ChanceQuotationItem> {

    private static final long serialVersionUID = 1L;

    public static final int DELIVER_INFO_LENGTH = 150;
    public static final int PAYMENT_LENGTH = 150;

    Chance chance;
    String deliverInfo;
    String payment;

    /**
     * 对应机会
     */
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    public Chance getChance() {
        return chance;
    }

    public void setChance(Chance chance) {
        if (chance == null)
            throw new NullPointerException("chance");
        this.chance = chance;
    }

    /**
     * 交付说明
     */
    @Column(length = DELIVER_INFO_LENGTH)
    public String getDeliverInfo() {
        return deliverInfo;
    }

    public void setDeliverInfo(String deliverInfo) {
        this.deliverInfo = deliverInfo;
    }

    /**
     * 付款说明
     */
    @Column(length = PAYMENT_LENGTH)
    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

}
