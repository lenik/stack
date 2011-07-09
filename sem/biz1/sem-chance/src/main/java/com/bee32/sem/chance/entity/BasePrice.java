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

    public static BasePrice tempQD1 = new BasePrice("宾得XR", 6000, "第一代");
    public static BasePrice tempQD2 = new BasePrice("宾得XR", 7000, "第二代");
    public static BasePrice tempQD3 = new BasePrice("宾得XR", 8000, "第三代");
    public static BasePrice tempQD4 = new BasePrice("宾得XR", 9000, "第四代");

    public static BasePrice tempQD5 = new BasePrice("猪肉", 5, "纯天然");
    public static BasePrice tempQD6 = new BasePrice("猪肉", 8, "无污染");
    public static BasePrice tempQD7 = new BasePrice("猪肉", 15, "添加剂");
    public static BasePrice tempQD8 = new BasePrice("猪肉", 20, "瘦肉精");
    public static BasePrice tempQD9 = new BasePrice("猪肉", 25, "还是吃牛肉吧");

}
