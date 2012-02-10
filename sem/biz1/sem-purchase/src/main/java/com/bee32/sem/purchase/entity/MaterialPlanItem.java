package com.bee32.sem.purchase.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
@SequenceGenerator(name = "idgen", sequenceName = "plan_item_seq", allocationSize = 1)
public class MaterialPlanItem
        extends MomentInterval
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    public static final int ADDITIONAL_REQUIREMENT_LENGTH = 200;

    MaterialPlan materialPlan;
    int index;
    Material material;
    BigDecimal quantity = new BigDecimal(0);

    Party preferredSupplier;
    String additionalRequirement;

    @NaturalId
    @ManyToOne(optional = false)
    public MaterialPlan getMaterialPlan() {
        return materialPlan;
    }

    public void setMaterialPlan(MaterialPlan materialPlan) {
        this.materialPlan = materialPlan;
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
     * 数量
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
                naturalId(materialPlan), //
                naturalId(material));
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        if (materialPlan == null)
            throw new NullPointerException("materialPlan");
        if (material == null)
            throw new NullPointerException("material");
        return selectors(//
                selector(prefix + "materialPlan", materialPlan), //
                selector(prefix + "material", material));
    }

    @Override
    public MaterialPlanItem detach() {
        super.detach();
        materialPlan = null;
        return this;
    }

}
