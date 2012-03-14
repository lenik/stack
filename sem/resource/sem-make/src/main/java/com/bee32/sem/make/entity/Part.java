package com.bee32.sem.make.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.plover.ox1.color.Green;
import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.inventory.entity.MaterialType;
import com.bee32.sem.inventory.util.ConsumptionMap;
import com.bee32.sem.make.service.PriceStrategy;
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
    Date validDateFrom = new Date();
    Date validDateTo;

    PriceStrategy priceStrategy = PriceStrategy.LATEST;

    BigDecimal wage = new BigDecimal(0);
    BigDecimal otherFee = new BigDecimal(0);
    BigDecimal electricityFee = new BigDecimal(0);
    BigDecimal equipmentCost = new BigDecimal(0);

    MaterialCategory category;

    List<Technic> techincs;

    public Part() {
        // Default valid duration = 1 year.
        Calendar cal = Calendar.getInstance();
        cal.setTime(validDateFrom);
        cal.add(Calendar.YEAR, 1);
        validDateTo = cal.getTime();
    }

    @Override
    public void populate(Object source) {
        if (source instanceof Part)
            _populate((Part) source);
        else
            super.populate(source);
    }

    protected void _populate(Part o) {
        super._populate(o);
        obsolete = o.obsolete;
        children = CopyUtils.copyList(o.children);
        xrefs = new ArrayList<PartItem>(o.xrefs);
        xrefCount = o.xrefCount;
        target = o.target;
        valid = o.valid;
        validDateFrom = o.validDateFrom;
        validDateTo = o.validDateTo;
        priceStrategy = o.priceStrategy;
        wage = o.wage;
        otherFee = o.otherFee;
        electricityFee = o.electricityFee;
        equipmentCost = o.equipmentCost;
        category = o.category;
        techincs = CloneUtils.cloneList(o.techincs);
    }

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
     * 冗余物料分类，便于统计某个物料分类下的BOM数量
     *
     * @return
     */
    @ManyToOne
    public MaterialCategory getCategory() {
        return category;
    }

    public void setCategory(MaterialCategory category) {
        this.category = category;
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

    public void addChild(Object materialOrPart, double quantity) {
        addChild(materialOrPart, new BigDecimal(quantity));
    }

    public void addChild(Object materialOrPart, BigDecimal quantity) {
        PartItem child = new PartItem();
        child.setParent(this);
        if (materialOrPart instanceof Material) {
            child.setPart(null);
            child.setMaterial((Material) materialOrPart);
        } else {
            child.setPart((Part) materialOrPart);
            child.setMaterial(null);
        }
        child.setQuantity(quantity);
        child.setValidDateFrom(validDateFrom);
        child.setValidDateTo(validDateTo);
        child.setValid(valid);
        addChild(child);
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
            throws FxrQueryException {
        BigDecimal price = priceStrategy.getPrice(this);
        return price;
    }

    /**
     * 工艺
     */
    @OneToMany(orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<Technic> getTechincs() {
        return techincs;
    }

    public void setTechincs(List<Technic> techincs) {
        this.techincs = techincs;
    }

    /**
     * @aka obtainAllMaterials
     */
    @Transient
    public ConsumptionMap getMaterialConsumption() {
        ConsumptionMap mc = new ConsumptionMap();
        collectConsumption(mc, BigDecimal.ONE);
        return mc;
    }

    void collectConsumption(ConsumptionMap mc, BigDecimal times) {
        for (PartItem child : children)
            if (child.getType() == MaterialType.RAW)
                mc.consume(child.getMaterial(), child.getQuantity().multiply(times));
            else
                child.getPart().collectConsumption(mc, child.getQuantity());
    }

}
