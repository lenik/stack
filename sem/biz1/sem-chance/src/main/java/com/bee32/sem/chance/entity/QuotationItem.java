package com.bee32.sem.chance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.bee32.plover.orm.ext.color.UIEntityAuto;

@Entity
public class QuotationItem
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 对应报价单
     */
    QuotationInvoice quotationInvoice;

    /**
     * 品名
     */
    String materialName;

    /**
     * 折扣
     */
    double discount;

    /**
     * 单价
     */
    double price;

    /**
     * 个数
     */
    int number;

    /**
     * 总额
     */
    double amount;

    /**
     * 备注
     */
    String remark;

    @ManyToOne
    public QuotationInvoice getQuotationInvoice() {
        return quotationInvoice;
    }

    public void setQuotationInvoice(QuotationInvoice quotationInvoice) {
        this.quotationInvoice = quotationInvoice;
    }

    @Column(nullable = false, length = 50)
    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Column(nullable = false, length = 200)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
