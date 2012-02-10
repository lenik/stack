package com.bee32.sem.bom.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DefaultValue;

import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.ox1.color.Green;
import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.bom.service.MaterialPriceNotFoundException;
import com.bee32.sem.bom.service.PriceStrategy;
import com.bee32.sem.inventory.Classification;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.world.monetary.FxrQueryException;

/**
 * 部件（包括成品和半成品）
 */
@Entity
@Green
@SequenceGenerator(name = "idgen", sequenceName = "part_seq", allocationSize = 1)
public class Part
        extends UIEntityAuto<Integer>
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    Part obsolete;

    List<PartItem> children = new ArrayList<PartItem>();

    List<PartItem> xrefs = new ArrayList<PartItem>();
    Integer xrefCount;

    Material target;

    boolean valid = true;
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
    public Part getObsolete() {
        return obsolete;
    }

    public void setObsolete(Part obsolete) {
        this.obsolete = obsolete;
    }

    /**
     * 目标物料
     */
    @ManyToOne
    public Material getTarget() {
        return target;
    }

    public void setTarget(Material target) {
        this.target = target;
    }

    /**
     * 子部件（半成品或原材料）
     */
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<PartItem> getChildren() {
        return children;
    }

    public void setChildren(List<PartItem> children) {
        if (children == null)
            throw new NullPointerException("children");
        this.children = children;
    }

    public boolean addChild(PartItem child) {
        if (child == null)
            throw new NullPointerException("child");
        if (children.contains(child))
            return false;
        children.add(child);
        return true;
    }

    public boolean removeChild(PartItem child) {
        if (child == null)
            throw new NullPointerException("child");
        return children.remove(child);
    }

    /**
     * 引用本部件的 PartItem。
     */
    @OneToMany(mappedBy = "part")
    public List<PartItem> getXrefs() {
        return xrefs;
    }

    public void setXrefs(List<PartItem> xrefs) {
        if (xrefs == null)
            throw new NullPointerException("xrefs");
        this.xrefs = xrefs;
        this.xrefCount = null;
    }

    /**
     *
     */
    @Redundant
    @Column(nullable = false)
    public int getXrefCount() {
        if (xrefCount == null) {
            xrefCount = xrefs.size();
        }
        return xrefCount;
    }

    public void setXrefCount(int xrefCount) {
        this.xrefCount = xrefCount;
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

    @Column(name = "priceStrategy", nullable = false)
    @DefaultValue("'z'")
    char get_priceStrategy() {
        return priceStrategy.getValue();
    }

    void set_priceStrategy(char strategy) {
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

    public Map<Material, BigDecimal> obtainAllMaterial() {
        Map<Material, BigDecimal> result = new HashMap<Material, BigDecimal>();
        for (PartItem item : children) {
            if (item.getClassification() == Classification.RAW) {
                result.put(item.getMaterial(), item.getQuantity());
            } else {
                Map<Material, BigDecimal> childMaterials = item.getPart().obtainAllMaterial();
                if (childMaterials != null) {
                    for (Material m : childMaterials.keySet()) {
                        BigDecimal q = result.get(m);

                        if (q != null) {
                            result.put(m, q.add(childMaterials.get(m)));
                        } else {
                            result.put(m, childMaterials.get(m));
                        }
                    }
                }
            }
        }

        if (result.size() > 0)
            return result;
        return null;
    }

}
