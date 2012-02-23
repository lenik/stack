package com.bee32.sem.chance.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.NaturalId;

import com.bee32.icsf.access.DefaultPermission;
import com.bee32.icsf.access.Permission;
import com.bee32.plover.ox1.color.Blue;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.world.thing.AbstractItem;

/**
 * 报价单里面的条目
 */
@Entity
@Blue
@DefaultPermission(Permission.R_X)
@SequenceGenerator(name = "idgen", sequenceName = "chance_quotation_item_seq", allocationSize = 1)
public class ChanceQuotationItem
        extends AbstractItem {

    private static final long serialVersionUID = 1L;

    ChanceQuotation parent;

    Material material;
    float discount = 1;

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

    @Transient
    @Override
    protected Date getFxrDate() {
        return parent.getBeginTime();
    }

    /**
     * 物料
     */
    @NaturalId(mutable = true)
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
