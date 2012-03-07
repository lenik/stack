package com.bee32.sem.purchase.entity;

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
import com.bee32.sem.bom.entity.Part;
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
    int index;
    Part part;


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

    @NaturalId
    @ManyToOne
    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
// if (part == null)
// throw new NullPointerException("part");
        this.part = part;
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
                naturalId(part));
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        return selectors(//
                selector(prefix + "parent", parent), //
                selector(prefix + "part", part));
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
