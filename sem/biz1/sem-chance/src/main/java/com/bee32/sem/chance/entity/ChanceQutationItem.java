package com.bee32.sem.chance.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NaturalId;

import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialPrice;
import com.bee32.sem.world.thing.GeneralOrderItem;

/**
 * 报价单里面的条目
 */
@Entity
public class ChanceQutationItem
        extends GeneralOrderItem {

    private static final long serialVersionUID = 1L;

    ChanceQuotation quotation;

    Material material;
    MaterialPrice basePrice;
    float discount;

    public ChanceQutationItem() {
    }

    /**
     * 对应报价单
     */
    @NaturalId
    @ManyToOne
    public ChanceQuotation getQuotation() {
        return quotation;
    }

    public void setQuotation(ChanceQuotation quotation) {
        this.quotation = quotation;
    }

    /**
     * 物料
     */
    @NaturalId
    @ManyToOne
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        if (material == null)
            throw new NullPointerException("material");
        this.material = material;
    }

    /**
     * 基准价格
     */
    @ManyToOne
    public MaterialPrice getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(MaterialPrice basePrice) {
        this.basePrice = basePrice;
    }

    /**
     * 折扣
     */
    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

}
