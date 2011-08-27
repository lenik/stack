package com.bee32.sem.purchase.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.And;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.ext.c.CEntityAuto;
import com.bee32.plover.orm.ext.config.DecimalConfig;
import com.bee32.sems.bom.entity.Part;

/**
 * 定单明细项目
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "make_order_item_seq", allocationSize = 1)
public class MakeOrderItem
        extends CEntityAuto<Long>
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    MakeOrder order;
    Part part;
    BigDecimal quantity = new BigDecimal(1);

    @NaturalId
    @ManyToOne(optional = false)
    public MakeOrder getOrder() {
        return order;
    }

    public void setOrder(MakeOrder order) {
        if (order == null)
            throw new NullPointerException("order");
        this.order = order;
    }

    @NaturalId
    @ManyToOne(optional = false)
    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        if (part == null)
            throw new NullPointerException("part");
        this.part = part;
    }

    @Column(precision = QTY_ITEM_PRECISION, scale = QTY_ITEM_SCALE, nullable = false)
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        if (quantity == null)
            throw new NullPointerException("quantity");
        this.quantity = quantity;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(order), //
                naturalId(part));
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        return new And(//
                selector(prefix + "order", order), //
                selector(prefix + "part", part));
    }

}
