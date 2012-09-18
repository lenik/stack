package com.bee32.sem.makebiz.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.bee32.sem.inventory.tx.entity.StockJob;

/**
 * 出库单
 *
 * 送货时，对应产品出库，这里即为对应的销售出库单
 */
@Entity
public class DeliveryNoteTakeOut
        extends StockJob {

    private static final long serialVersionUID = 1L;

    DeliveryNote deliveryNote;

    /**
     * 送货单
     *
     * 本销售出库单对应的送货单。
     *
     * @return
     */
    @OneToOne(optional = false)
    public DeliveryNote getDeliveryNote() {
        return deliveryNote;
    }

    public void setDeliveryNote(DeliveryNote deliveryNote) {
        this.deliveryNote = deliveryNote;
    }

}
