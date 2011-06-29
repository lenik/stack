package com.bee32.sem.chance.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.ext.color.UIEntityAuto;
import com.bee32.sem.people.entity.Party;

@Entity
public class QuotationInvoice
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 报价人
     */
    User owner;

    /**
     * 主题
     */
    String subject;
    /**
     * 对应机会
     */
    Chance chance;
    /**
     * 客户
     */
    List<Party> parties = new ArrayList<Party>();
    /**
     * 明细
     */
    List<QuotationItem> items = new ArrayList<QuotationItem>();
    // redundancy
    double amount = 0.0;

    /**
     * 交付说明
     */
    String recommend;

    /**
     * 付款说明
     */
    String payment;
    /**
     * 备注
     */
    String remark;

    @ManyToOne
    @JoinColumn(nullable = false)
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Column(nullable = false, length = 50)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @ManyToOne
    @JoinColumn(nullable = false)
    public Chance getChance() {
        return chance;
    }

    public void setChance(Chance chance) {
        this.chance = chance;
    }

    @ManyToMany
    public List<Party> getParties() {
        return parties;
    }

    public void setParties(List<Party> parties) {
        this.parties = parties;
    }

    @OneToMany(mappedBy="quotationInvoice")
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

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
