package com.bee32.sem.makebiz.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.c.CEntity;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.world.thing.AbstractItem;

/**
 * 送货单明细项目
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "delivery_note_item_seq", allocationSize = 1)
public class DeliveryNoteItem
        extends AbstractItem
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    DeliveryNote parent;
    Material material;

    MakeOrderItem orderItem;

    StockWarehouse sourceWarehouse;

    @NaturalId
    @ManyToOne(optional = false)
    public DeliveryNote getParent() {
        return parent;
    }

    public void setParent(DeliveryNote parent) {
        if (parent == null)
            throw new NullPointerException("parent");
        this.parent = parent;
    }

    @NaturalId(mutable = true)
    @ManyToOne
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @ManyToOne
    public MakeOrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(MakeOrderItem orderItem) {
        this.orderItem = orderItem;
    }

    @ManyToOne
    public StockWarehouse getSourceWarehouse() {
        return sourceWarehouse;
    }

    public void setSourceWarehouse(StockWarehouse sourceWarehouse) {
        this.sourceWarehouse = sourceWarehouse;
    }

    @Transient
    @Override
    protected Date getFxrDate() {
        return parent.getBeginTime();
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(parent), //
                naturalId(material));
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        return selectors(//
                selector(prefix + "parent", parent), //
                selector(prefix + "material", material));
    }

    @Override
    public DeliveryNoteItem detach() {
        super.detach();
        parent = null;
        return this;
    }

    @Override
    protected CEntity<?> owningEntity() {
        return getParent();
    }

}
