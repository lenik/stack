package com.bee32.sem.makebiz.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.bee32.sem.inventory.tx.entity.StockJob;

/**
 * 送货单对应的销售出库单
 */
@Entity
public class DeliveryNoteTakeOut
        extends StockJob {

    private static final long serialVersionUID = 1L;

    DeliveryNote deliveryNote;

    @OneToOne(optional = false)
    public DeliveryNote getDeliveryNote() {
        return deliveryNote;
    }

    public void setDeliveryNote(DeliveryNote deliveryNote) {
        this.deliveryNote = deliveryNote;
    }

}
