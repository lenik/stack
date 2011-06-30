package com.bee32.sem.chance.web;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.plover.orm.util.EntityViewBean;

@Component
@Scope("view")
public class PriceViewBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    static String materialPattern;

    private String materialName;
    private double discount;
    private double price;
    private String remark;

    public String getMaterialPattern() {
        return materialPattern;
    }

    public void setMaterialPattern(String materialPattern) {
        PriceViewBean.materialPattern = materialPattern;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
