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
import com.bee32.sem.material.entity.Material;
import com.bee32.sem.material.entity.StockWarehouse;
import com.bee32.sem.world.thing.AbstractItem;

/**
 * 送货明细
 *
 * 送货单明细项目。
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

    /**
     * 送货单
     *
     * 送货单主控类。
     *
     * @return
     */
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

    /**
     * 物料(产品)
     *
     * 送货明细对应的物料，一般是成品。
     *
     * @return
     */
    @NaturalId(mutable = true)
    @ManyToOne
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }


    /**
     * 定单明细
     *
     * 为了在定单上可以看到送货情况，送货明细和定单明细一一对应，这里反应出这个关系。
     *
     * @return
     */
    @ManyToOne
    public MakeOrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(MakeOrderItem orderItem) {
        this.orderItem = orderItem;
    }

    /**
     * 仓库
     *
     * 送货时，出库产品对应的出库仓库。
     *
     * @return
     */
    @ManyToOne
    public StockWarehouse getSourceWarehouse() {
        return sourceWarehouse;
    }

    public void setSourceWarehouse(StockWarehouse sourceWarehouse) {
        this.sourceWarehouse = sourceWarehouse;
    }

    /**
     * 汇率时间
     *
     * 汇率每天都有变化，则本时间来指定取数的时间点。
     */
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
