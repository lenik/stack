package com.bee32.sem.purchase.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.color.MomentInterval;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.people.entity.Party;

/**
 * 物料计划明细项目
 *
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "purchase_request_item_seq", allocationSize = 1)
public class PurchaseRequestItem
        extends MomentInterval
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    public static final int ADDITIONAL_REQUIREMENT_LENGTH = 200;

    PurchaseRequest purchaseRequest;
    int index;
    Material material;
    BigDecimal quantity = new BigDecimal(0);
    BigDecimal planQuantity = new BigDecimal(0);

    Party preferredSupplier;
    String additionalRequirement;

    List<Inquiry> inquiries;

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

    @NaturalId(mutable=true)
    @ManyToOne(optional = false)
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     * 从物料需求计算过来的数量(请求数量)
     *
     * <p>
     * 精度限制：小数点后4位数字。如果需要超出该精度，应考虑为对应物品采用不同的单位。
     * <p>
     * <fieldset> <legend> 关于数量的单词 Amount/Quantity/Number: </legend>
     * <ul>
     * <li>Number of ...: 可数的/整数的
     * <li>Amount of ...: 不可测量的
     * <li>Quantity of ...: 可测量的
     * <ul>
     * </fieldset>
     *
     * @see http://english.stackexchange.com/questions/9439/amount-vs-number
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


    /**
     * 采购项目对应的询价
     */
    @OneToMany(mappedBy = "purchaseReqeustItem")
    public List<Inquiry> getInquiries() {
        return inquiries;
    }

    public void setInquiries(List<Inquiry> inquiries) {
        this.inquiries = inquiries;
    }
}
