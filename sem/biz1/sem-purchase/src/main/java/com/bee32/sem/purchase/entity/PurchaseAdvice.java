package com.bee32.sem.purchase.entity;

import javax.persistence.Column;
import javax.persistence.OneToOne;

import com.bee32.sem.base.tx.TxEntity;

/**
 * 采购建议
 *
 */
public class PurchaseAdvice extends TxEntity {

    private static final long serialVersionUID = 1L;

    public static final int REASON_LENGTH = 500;

    Inquiry preferredInquiry;
    String reason;

    PurchaseRequestItem purchaseRequestItem;

    /**
     * 建议选择的供应商报价
     */
    @OneToOne
    public Inquiry getPreferredInquiry() {
        return preferredInquiry;
    }

    public void setPreferredInquiry(Inquiry preferredInquiry) {
        this.preferredInquiry = preferredInquiry;
    }

    /**
     * 理由
     * @return
     */
    @Column(length = REASON_LENGTH)
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * 对应的采购请求条目
     */
    @OneToOne(mappedBy = "purchaseAdvice")
    public PurchaseRequestItem getPurchaseRequestItem() {
        return purchaseRequestItem;
    }

    public void setPurchaseRequestItem(PurchaseRequestItem purchaseRequestItem) {
        this.purchaseRequestItem = purchaseRequestItem;
    }
}
