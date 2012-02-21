package com.bee32.sem.purchase.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.base.tx.TxEntity;
import com.bee32.sem.people.entity.Org;
import com.bee32.sem.world.monetary.MCValue;

/**
 * 采购询价
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "purchase_inquiry_seq", allocationSize = 1)
public class PurchaseInquiry
        extends TxEntity
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    public static final int DELIVERY_DATE_LENGTH = 30;
    public static final int QUALITY_LENGTH = 120;
    public static final int PAYMENT_TERM_LENGTH = 60;
    public static final int AFTER_SERVICE_LENGTH = 60;
    public static final int OTHER_LENGTH = 1000;
    public static final int COMMENT_LENGTH = 200;

    PurchaseRequestItem parent;
    Org supplier;
    MCValue price = new MCValue();
    String deliveryDate;
    String quality;
    String paymentTerm;
    String afterService;
    String other;
    String comment;

    /**
     * 需要询价的采购项目
     */
    @ManyToOne(optional = false)
    @NaturalId
    public PurchaseRequestItem getParent() {
        return parent;
    }

    public void setParent(PurchaseRequestItem parent) {
        if (parent == null)
            throw new NullPointerException("parent");
        this.parent = parent;
    }

    /**
     * 供应商
     */
    @ManyToOne
    @NaturalId
    public Org getSupplier() {
        return supplier;
    }

    public void setSupplier(Org supplier) {
        this.supplier = supplier;
    }

    /**
     * 价格
     */
    public MCValue getPrice() {
        return price;
    }

    public void setPrice(MCValue price) {
        this.price = price;
    }

    /**
     * 交货时间，类型为String, 举例： 50天，2周 etc.
     */
    @Column(length = DELIVERY_DATE_LENGTH)
    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    /**
     * 质量
     */
    @Column(length = QUALITY_LENGTH)
    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    /**
     * 付款方式
     */
    @Column(length = PAYMENT_TERM_LENGTH)
    public String getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(String paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    /**
     * 售后服务
     */
    @Column(length = AFTER_SERVICE_LENGTH)
    public String getAfterService() {
        return afterService;
    }

    public void setAfterService(String afterService) {
        this.afterService = afterService;
    }

    /**
     * 其他
     */
    @Column(length = OTHER_LENGTH)
    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    /**
     * 询价建议的理由
     */
    @Column(length = COMMENT_LENGTH)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
