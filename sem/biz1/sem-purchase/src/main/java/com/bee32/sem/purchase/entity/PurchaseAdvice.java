package com.bee32.sem.purchase.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.sem.base.tx.TxEntity;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.builtin.ISingleVerifier;
import com.bee32.sem.process.verify.builtin.SingleVerifierSupport;

/**
 * 采购建议
 *
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "purchase_advice_seq", allocationSize = 1)
public class PurchaseAdvice
    extends TxEntity
    implements
        IVerifiable<ISingleVerifier> {

    private static final long serialVersionUID = 1L;

    public static final int REASON_LENGTH = 500;

    Inquiry preferredInquiry;
    String reason;

    PurchaseRequestItem purchaseRequestItem;

    public PurchaseAdvice() {
        setVerifyContext(new SingleVerifierSupport());
    }

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

    SingleVerifierSupport verifyContext;

    @Embedded
    @Override
    public SingleVerifierSupport getVerifyContext() {
        return verifyContext;
    }

    public void setVerifyContext(SingleVerifierSupport verifyContext) {
        this.verifyContext = verifyContext;
    }

}
