package com.bee32.sem.chance.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NaturalId;

import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.world.thing.AbstractOrderItem;

/**
 * 报价单里面的条目
 */
@Entity
public class ChanceQuotationItem
        extends AbstractOrderItem {

    private static final long serialVersionUID = 1L;

    ChanceQuotation parent;

    Material material;
    float discount;

    /**
     * 对应报价单
     */
    @NaturalId
    @ManyToOne
    public ChanceQuotation getParent() {
        return parent;
    }

    public void setParent(ChanceQuotation parent) {
        if (parent == null)
            throw new NullPointerException("parent");
        this.parent = parent;
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
     * 折扣
     */
    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

}
