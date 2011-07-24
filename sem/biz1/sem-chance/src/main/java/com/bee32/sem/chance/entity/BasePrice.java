package com.bee32.sem.chance.entity;

import javax.persistence.Entity;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.orm.ext.color.Green;
/**
 * 物料基础价格
 */
@Entity
@Green
public class BasePrice
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    String material;
    double price;
    String remark;

    public BasePrice() {
    }

    public BasePrice(String material,  double price, String remark) {
        this.material = material;
        this.price = price;
        this.remark = remark;
    }

    /**
     * 物料
     */
    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    /**
     * 基础价格
     */
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
