package com.bee32.sems.bom.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.CriteriaComposite;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.ext.config.DecimalConfig;
import com.bee32.plover.orm.ext.tree.TreeEntityAuto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sems.bom.service.MaterialPriceNotFoundException;
import com.bee32.sems.bom.service.PriceStrategy;

/**
 * 零件 （包括成品和半成品）
 */
@Entity
public class Component
        extends TreeEntityAuto<Long, Component>
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    Component obsolete;

    Material material;
    BigDecimal quantity;

    boolean valid;
    Date validDateFrom;
    Date validDateTo;

    PriceStrategy priceStrategy = PriceStrategy.LATEST;

    BigDecimal wage = new BigDecimal(0);
    BigDecimal otherFee = new BigDecimal(0);
    BigDecimal electricityFee = new BigDecimal(0);
    BigDecimal equipmentCost = new BigDecimal(0);

    /**
     * 上一个版本。
     */
    @ManyToOne(fetch = FetchType.LAZY)
    public Component getObsolete() {
        return obsolete;
    }

    public void setObsolete(Component obsolete) {
        this.obsolete = obsolete;
    }

    /**
     * 对应物料
     */
    @NaturalId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        if (material == null)
            throw new NullPointerException("material");
        this.material = material;
    }

    /**
     * 数量
     */
    @Column(precision = QTY_ITEM_PRECISION, scale = QTY_ITEM_SCALE, nullable = false)
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        if (quantity == null)
            throw new NullPointerException("quantity");
        this.quantity = quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = new BigDecimal(quantity);
    }

    public void setQuantity(double quantity) {
        this.quantity = new BigDecimal(quantity);
    }

    /**
     * 是否有效
     */
    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * 起用日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getValidDateFrom() {
        return validDateFrom;
    }

    public void setValidDateFrom(Date validDateFrom) {
        this.validDateFrom = validDateFrom;
    }

    /**
     * 无效日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getValidDateTo() {
        return validDateTo;
    }

    public void setValidDateTo(Date validDateTo) {
        this.validDateTo = validDateTo;
    }

    /**
     * 价格策略。（默认使用 最新价格策略）
     */
    @Transient
    public PriceStrategy getPriceStrategy() {
        return priceStrategy;
    }

    public void setPriceStrategy(PriceStrategy priceStrategy) {
        if (priceStrategy == null)
            throw new NullPointerException("priceStrategy");
        this.priceStrategy = priceStrategy;
    }

    @Column(name = "priceStrategy", length = 3)
    String getPriceStrategy_() {
        return priceStrategy.getValue();
    }

    void setPriceStrategy_(String strategy) {
        if (strategy == null)
            throw new NullPointerException("strategy");
        priceStrategy = PriceStrategy.valueOf(strategy);
    }

    /**
     * 工资
     */
    @Column(precision = MONEY_ITEM_PRECISION, scale = MONEY_ITEM_SCALE, nullable = false)
    public BigDecimal getWage() {
        return wage;
    }

    public void setWage(BigDecimal wage) {
        if (wage == null)
            throw new NullPointerException("wage");
        this.wage = wage;
    }

    /**
     * 其它费用
     */
    @Column(precision = MONEY_ITEM_PRECISION, scale = MONEY_ITEM_SCALE, nullable = false)
    public BigDecimal getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(BigDecimal otherFee) {
        if (otherFee == null)
            throw new NullPointerException("otherFee");
        this.otherFee = otherFee;
    }

    /**
     * 电费
     */
    @Column(precision = MONEY_ITEM_PRECISION, scale = MONEY_ITEM_SCALE, nullable = false)
    public BigDecimal getElectricityFee() {
        return electricityFee;
    }

    public void setElectricityFee(BigDecimal electricityFee) {
        if (electricityFee == null)
            throw new NullPointerException("electricityFee");
        this.electricityFee = electricityFee;
    }

    /**
     * 设备费
     */
    @Column(precision = MONEY_ITEM_PRECISION, scale = MONEY_ITEM_SCALE, nullable = false)
    public BigDecimal getEquipmentCost() {
        return equipmentCost;
    }

    public void setEquipmentCost(BigDecimal equipmentCost) {
        if (equipmentCost == null)
            throw new NullPointerException("equipmentCost");
        this.equipmentCost = equipmentCost;
    }

    /**
     * 工资、设备等额外费用。
     */
    @Transient
    public BigDecimal getExtraCost() {
        BigDecimal total = new BigDecimal(0);
        total = total.add(wage);
        total = total.add(otherFee);
        total = total.add(electricityFee);
        total = total.add(equipmentCost);
        return total;
    }

    public BigDecimal calcPrice()
            throws MaterialPriceNotFoundException, FxrQueryException {
        BigDecimal price = priceStrategy.getPrice(this);
        return price;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(getParent()), //
                naturalId(material));
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        return new CriteriaComposite( //
                selector(prefix + "parent", getParent()), //
                selector(prefix + "material", material));
    }

}
