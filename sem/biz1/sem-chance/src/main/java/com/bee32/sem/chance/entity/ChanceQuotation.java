package com.bee32.sem.chance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.bee32.sem.world.thing.AbstractOrder;

/**
 * 报价单
 */
@Entity
public class ChanceQuotation
        extends AbstractOrder<ChanceQuotationItem> {

    private static final long serialVersionUID = 1L;

    Chance chance;
    String subject;
    String recommend;
    String payment;

    /**
     * 对应机会
     */
    @ManyToOne
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
     * 主题
     */
    @Column(nullable = false, length = 50)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        if (subject == null)
            throw new NullPointerException("subject");
        this.subject = subject;
    }

    /**
     * 交付说明
     */
    @Column(length = 150)
    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    /**
     * 付款说明
     */
    @Column(length = 150)
    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

}
