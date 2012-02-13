package com.bee32.sem.purchase.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.process.base.ProcessEntity;

/**
 * 采购请求明细项目
 *
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "purchase_request_item_seq", allocationSize = 1)
public class PurchaseRequestItem
        extends ProcessEntity
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    public static final int ADDITIONAL_REQUIREMENT_LENGTH = 200;
    public static final int ADVICED_REASON_LENGTH = 500;

    PurchaseRequest purchaseRequest;
    int index;
    Material material;
    BigDecimal quantity = new BigDecimal(0);
    BigDecimal planQuantity = new BigDecimal(0);

    Party preferredSupplier;
    String additionalRequirement;

    List<PurchaseInquiry> inquiries = new ArrayList<PurchaseInquiry>();

    PurchaseInquiry advicedInquiry;
    String advicedReason;

    @NaturalId
    @ManyToOne(optional = false)
    public PurchaseRequest getPurchaseRequest() {
        return purchaseRequest;
    }

    public void setPurchaseRequest(PurchaseRequest purchaseRequest) {
        this.purchaseRequest = purchaseRequest;
    }

    /**
     * 单据内部的序号
     */
    @Column(nullable = false)
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @NaturalId(mutable = true)
    @ManyToOne(optional = false)
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     * 从物料需求计算复制过来的数量(请求数量)
     */
    @Column(scale = QTY_ITEM_SCALE, precision = QTY_ITEM_PRECISION, nullable = false)
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        if (quantity == null)
            throw new NullPointerException("quantity");
        this.quantity = quantity;
    }

    /**
     * 实际计划采购的数量(计划数量)
     */
    @Column(scale = QTY_ITEM_SCALE, precision = QTY_ITEM_PRECISION, nullable = false)
    public BigDecimal getPlanQuantity() {
        return planQuantity;
    }

    public void setPlanQuantity(BigDecimal planQuantity) {
        if (planQuantity == null)
            throw new NullPointerException("planQuantity");
        this.planQuantity = planQuantity;
    }

    @ManyToOne
    public Party getPreferredSupplier() {
        return preferredSupplier;
    }

    public void setPreferredSupplier(Party preferredSupplier) {
        this.preferredSupplier = preferredSupplier;
    }

    /**
     * 如客户指定产品需要哪种原材料
     */
    @Column(length = ADDITIONAL_REQUIREMENT_LENGTH)
    public String getAdditionalRequirement() {
        return additionalRequirement;
    }

    public void setAdditionalRequirement(String additionalRequirement) {
        this.additionalRequirement = additionalRequirement;
    }

    /**
     * 采购项目对应的询价
     */
    @OneToMany(mappedBy = "purchaseRequestItem", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<PurchaseInquiry> getInquiries() {
        return inquiries;
    }

    public void setInquiries(List<PurchaseInquiry> inquiries) {
        this.inquiries = inquiries;
    }

    public synchronized void addInquiry(PurchaseInquiry inquiry) {
        if (inquiry == null)
            throw new NullPointerException("inquiry");
        inquiries.add(inquiry);
    }

    public synchronized void removeInquiry(PurchaseInquiry inquiry) {
        if (inquiry == null)
            throw new NullPointerException("inquiry");

        int index = inquiries.indexOf(inquiry);
        if (index == -1)
            return /* false */;

        inquiries.remove(index);
        inquiry.detach();
    }

    /**
     * 询价建议
     */
    @OneToOne
    public PurchaseInquiry getAdvicedInquiry() {
        return advicedInquiry;
    }

    public void setAdvicedInquiry(PurchaseInquiry advicedInquiry) {
        this.advicedInquiry = advicedInquiry;
    }

    /**
     * 询价建议的理由
     */
    @Column(length = ADVICED_REASON_LENGTH)
    public String getAdvicedReason() {
        return advicedReason;
    }

    public void setAdvicedReason(String advicedReason) {
        this.advicedReason = advicedReason;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(purchaseRequest), //
                naturalId(material));
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        if (purchaseRequest == null)
            throw new NullPointerException("purchaseRequest");
        if (material == null)
            throw new NullPointerException("material");
        return selectors(//
                selector(prefix + "purchaseRequest", purchaseRequest), //
                selector(prefix + "material", material));
    }

    @Override
    public PurchaseRequestItem detach() {
        super.detach();
        purchaseRequest = null;
        return this;
    }

}
