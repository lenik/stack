package com.bee32.sem.purchase.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.bee32.sem.inventory.tx.entity.StockJob;

/**
 * 采购请求入库单
 *
 * 采购请求对应的采购入库单。在采购完成后，可根据采购请求的明细列表，自动生成入库单。
 *
 * <p lang="en">
 * Purchase Stock Take-In
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

    /**
     * 采购请示
     *
     * 本采购入库对应的采购请求。
     *
     * @return
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public PurchaseRequest getPurchaseRequest() {
        return purchaseRequest;
    }

    public void setPurchaseRequest(PurchaseRequest purchaseRequest) {
        this.purchaseRequest = purchaseRequest;
    }

}
