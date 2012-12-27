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
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.plover.ox1.c.CEntity;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.material.entity.Material;
import com.bee32.sem.material.entity.StockWarehouse;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.process.base.ProcessEntity;

/**
 * 采购请求明细项目
 *
 * 某个采购请求中所需要采购物料的明细列表。
 *
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "purchase_request_item_seq", allocationSize = 1)
public class PurchaseRequestItem
        extends ProcessEntity
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    public static final int ADDITIONAL_REQUIREMENT_LENGTH = 200;

    PurchaseRequest parent;
    int index;
    Material material;
    BigDecimal requiredQuantity = new BigDecimal(0);
    BigDecimal quantity = new BigDecimal(0);

    Party preferredSupplier;
    String additionalRequirement;

    List<PurchaseInquiry> inquiries = new ArrayList<PurchaseInquiry>();

    PurchaseInquiry acceptedInquiry;
    StockWarehouse destWarehouse;

    int inquiryCount;

    @Override
    public void populate(Object source) {
        if (source instanceof PurchaseRequestItem)
            _populate((PurchaseRequestItem) source);
        else
            super.populate(source);
    }

    protected void _populate(PurchaseRequestItem o) {
        super._populate(o);
        parent = o.parent;
        index = o.index;
        material = o.material;
        requiredQuantity = o.requiredQuantity;
        quantity = o.quantity;
        preferredSupplier = o.preferredSupplier;
        additionalRequirement = o.additionalRequirement;
        inquiries = CopyUtils.copyList(o.inquiries);
        acceptedInquiry = o.acceptedInquiry;
        destWarehouse = o.destWarehouse;
    }

    /**
     * 采购请求
     *
     * 采购请求明细对应的主控类。
     *
     * @return
     */
    @NaturalId
    @ManyToOne(optional = false)
    public PurchaseRequest getParent() {
        return parent;
    }

    public void setParent(PurchaseRequest parent) {
        this.parent = parent;
    }

    /**
     * 序号
     *
     * 单据内部的序号,显示的时候起作用。
     */
    @Column(nullable = false)
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * 物料
     *
     * 需要采购的物料。
     *
     * @return
     */
    @NaturalId(mutable = true)
    @ManyToOne(optional = false)
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     * 请求数量
     *
     * 从物料需求计算复制过来的数量。
     */
    @Column(scale = QTY_ITEM_SCALE, precision = QTY_ITEM_PRECISION, nullable = false)
    public BigDecimal getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(BigDecimal requiredQuantity) {
        if (requiredQuantity == null)
            throw new NullPointerException("requiredQuantity");
        this.requiredQuantity = requiredQuantity;
    }

    /**
     * 实际数量
     *
     * 实际需要采购的数量。
     */
    @Column(scale = QTY_ITEM_SCALE, precision = QTY_ITEM_PRECISION, nullable = false)
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal planQuantity) {
        if (planQuantity == null)
            throw new NullPointerException("planQuantity");
        this.quantity = planQuantity;
    }

    /**
     * 优选供应商
     *
     * 一般一个企业往往和某些供应商和长期的合作关系，这里的优选供应商就是指这个意思。
     *
     * @return
     */
    @ManyToOne
    public Party getPreferredSupplier() {
        return preferredSupplier;
    }

    public void setPreferredSupplier(Party preferredSupplier) {
        this.preferredSupplier = preferredSupplier;
    }

    /**
     * 采购项目的附加要求
     *
     * 如客户指定产品需要哪种原材料。
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
     *
     * 一个采购项目，对应很多询价建议。
     */
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
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
     *
     * 采购审核人在对比过所有的供应商报价后，给出选择哪个供应商的建议。
     */
    @OneToOne
    public PurchaseInquiry getAcceptedInquiry() {
        return acceptedInquiry;
    }

    public void setAcceptedInquiry(PurchaseInquiry acceptedInquiry) {
        this.acceptedInquiry = acceptedInquiry;
    }

    /**
     * 目标仓库
     *
     * 采购入库时对应的入库仓库。
     *
     * @return
     */
    @ManyToOne
    public StockWarehouse getDestWarehouse() {
        return destWarehouse;
    }

    public void setDestWarehouse(StockWarehouse destWarehouse) {
        this.destWarehouse = destWarehouse;
    }

    /**
     * 报价总数
     *
     * 统计有几个供应商报价。
     *
     * @return
     */
    @Formula("(select count(*) from purchase_inquiry i where i.parent=id)")
    public int getInquiryCount() {
        return inquiryCount;
    }

    public void setInquiryCount(int inquiryCount) {
        this.inquiryCount = inquiryCount;
    }


    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(parent), //
                naturalId(material));
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        if (parent == null)
            throw new NullPointerException("purchaseRequest");
        if (material == null)
            throw new NullPointerException("material");
        return selectors(//
                selector(prefix + "purchaseRequest", parent), //
                selector(prefix + "material", material));
    }

    @Override
    public PurchaseRequestItem detach() {
        super.detach();
        parent = null;
        return this;
    }

    @Override
    protected CEntity<?> owningEntity() {
        return parent;
    }

}
