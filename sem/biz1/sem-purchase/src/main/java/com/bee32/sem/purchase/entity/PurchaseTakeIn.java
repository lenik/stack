package com.bee32.sem.purchase.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.bee32.sem.inventory.tx.entity.StockJob;

/**
 * 采购请求对应的采购入库单
 */
@Entity
public class PurchaseTakeIn
        extends StockJob {

    private static final long serialVersionUID = 1L;

    PurchaseRequest purchaseRequest;

    @Override
    public void populate(Object source) {
        if (source instanceof PurchaseTakeIn)
            _populate((PurchaseTakeIn) source);
        else
            super.populate(source);
    }

    protected void _populate(PurchaseTakeIn o) {
        super._populate(o);
        purchaseRequest = o.purchaseRequest;
    }

    @ManyToOne(optional = false)
    public PurchaseRequest getPurchaseRequest() {
        return purchaseRequest;
    }

    public void setPurchaseRequest(PurchaseRequest purchaseRequest) {
        this.purchaseRequest = purchaseRequest;
    }

}
