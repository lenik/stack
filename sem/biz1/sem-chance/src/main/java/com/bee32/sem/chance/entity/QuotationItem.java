package com.bee32.sem.chance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.bee32.plover.orm.entity.EntityAuto;
/**
 * 报价单里面的条目
 */
@Entity
public class QuotationItem
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 对应报价单
     */
    Quotation quotation;

    BasePrice basePrice;

    /**
     * 品名
     */
    String material;

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
     * 备注
     */
    String remark;

    public QuotationItem() {
    }

    @ManyToOne
    public Quotation getQuotation() {
        return quotation;
    }

    public void setQuotation(Quotation quotation) {
        this.quotation = quotation;
    }

    public BasePrice getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BasePrice basePrice) {
        this.basePrice = basePrice;
    }

    @Column(nullable = false, length = 50)
    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
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

    @Column(nullable = false, length = 200)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
