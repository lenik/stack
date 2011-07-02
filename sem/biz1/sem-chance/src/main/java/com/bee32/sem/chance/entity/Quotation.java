package com.bee32.sem.chance.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.ext.color.UIEntityAuto;

/**
 * 报价单
 */
@Entity
public class Quotation
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    User creator;
    String subject;
    Chance chance;
    List<QuotationItem> items = new ArrayList<QuotationItem>();
    double amount = 0.0;
    String recommend;
    String payment;
    String remark;

    public Quotation() {
    }

    /**
     * 报价人
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    /**
     * 主题
     */
    @Column(nullable = false, length = 50)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * 对应机会
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    public Chance getChance() {
        return chance;
    }

    public void setChance(Chance chance) {
        this.chance = chance;
    }

    /**
     * 明细
     */
    @OneToMany(mappedBy = "quotation")
    @Cascade(CascadeType.DELETE)
    public List<QuotationItem> getItems() {
        return items;
    }

    public void setItems(List<QuotationItem> items) {
        this.items = items;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * 交付说明
     */
    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    /**
     * 付款说明
     */
    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    /**
     * 备注
     */
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
