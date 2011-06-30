package com.bee32.sem.chance.entity;

import javax.persistence.Entity;

import com.bee32.plover.orm.ext.color.UIEntityAuto;

@Entity
public class QuotationDetail
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    String material;
    double discount;
    double price;
    String remark;

    public QuotationDetail(String material, double discount, double price, String remark) {
        super();
        this.material = material;
        this.discount = discount;
        this.price = price;
        this.remark = remark;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static QuotationDetail tempQD1 = new QuotationDetail("宾得XR", 0.95, 6000, "第一代");
    public static QuotationDetail tempQD2 = new QuotationDetail("宾得XR", 0.95, 7000, "第二代");
    public static QuotationDetail tempQD3 = new QuotationDetail("宾得XR", 0.95, 8000, "第三代");
    public static QuotationDetail tempQD4 = new QuotationDetail("宾得XR", 0.95, 9000, "第四代");

    public static QuotationDetail tempQD5 = new QuotationDetail("猪肉", 0.95, 5, "纯天然");
    public static QuotationDetail tempQD6 = new QuotationDetail("猪肉", 0.95, 8, "无污染");
    public static QuotationDetail tempQD7 = new QuotationDetail("猪肉", 0.95, 15, "添加剂");
    public static QuotationDetail tempQD8 = new QuotationDetail("猪肉", 0.95, 20, "瘦肉精");
    public static QuotationDetail tempQD9 = new QuotationDetail("猪肉", 0.95, 25, "还是吃牛肉吧");


}
