package com.bee32.sem.inventory.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.cache.Redundant;
import com.bee32.sem.file.entity.UserFile;
import com.bee32.sem.world.monetary.MCValue;
import com.bee32.sem.world.thing.Thing;

/**
 * 物料
 *
 * 物品材料。
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "material_seq", allocationSize = 1)
public class Material
        extends Thing<MaterialXP> {

    private static final long serialVersionUID = 1L;

    MaterialCategory category;
    int alarmAhead;

    List<MaterialAttribute> attributes = new ArrayList<MaterialAttribute>();
    List<UserFile> attachments = new ArrayList<UserFile>();

    List<MaterialWarehouseOption> options = new ArrayList<MaterialWarehouseOption>();
    List<MaterialPreferredLocation> preferredLocations = new ArrayList<MaterialPreferredLocation>();

    List<MaterialPrice> prices = new ArrayList<MaterialPrice>();

    // ------------------------------------------------------------------------
    // 需要索引的常用的物料属性（这些属性和单位换算无关）。
    //
    int packageWidth;
    int packageHeight;
    int packageLength;
    int packageWeight;
    int netWeight;

    // ------------------------------------------------------------------------

    public Material() {
        super();
    }

    public Material(String name) {
        super(name);
    }

    @Override
    public void populate(Object source) {
        if (source instanceof Material) {
            Material o = (Material) source;
            _populate(o);
        } else
            super.populate(source);
    }

    protected void _populate(Material o) {
        super._populate(o);
        category = o.category;
        alarmAhead = o.alarmAhead;
        // attributes = new ArrayList<>(o.attributes);
        // options = new ArrayList<>(o.options);
        // prices = new ArrayList<>(o.prices);
        packageWidth = o.packageWidth;
        packageHeight = o.packageHeight;
        packageLength = o.packageLength;
        packageWeight = o.packageWeight;
        netWeight = o.netWeight;
    }

    @Override
    public void retarget(Object o) {
        super.retarget(o);
        _retarget((Material) o);
    }

    private void _retarget(Material o) {
        _retargetMerge(attributes, o.attributes);
        _retargetMerge(options, o.options);
        _retargetMerge(preferredLocations, o.preferredLocations);
    }

    @Transient
    public String getDisplayName() {
        return label;
    }

    /**
     * 分类
     *
     * 物料的类别。
     */
    @ManyToOne(optional = false)
    public MaterialCategory getCategory() {
        return category;
    }

    public void setCategory(MaterialCategory category) {
        if (category == null)
            throw new NullPointerException("category");
        this.category = category;
    }

    /**
     * 预警
     *
     * 当库存中的物料不够充足时提前报警的天数。
     */
    @Column(nullable = false)
    public int getAlarmAhead() {
        return alarmAhead;
    }

    public void setAlarmAhead(int alarmAhead) {
        this.alarmAhead = alarmAhead;
    }

    /**
     * 宽度
     *
     * 装箱的宽度（单位：厘米）。
     */
    public int getPackageWidth() {
        return packageWidth;
    }

    public void setPackageWidth(int packageWidth) {
        this.packageWidth = packageWidth;
    }

    /**
     * 高度
     *
     * 装箱的高度（单位：厘米）。
     */
    public int getPackageHeight() {
        return packageHeight;
    }

    public void setPackageHeight(int packageHeight) {
        this.packageHeight = packageHeight;
    }

    /**
     * 长度
     *
     * 装箱的长度（单位：厘米）。
     */
    public int getPackageLength() {
        return packageLength;
    }

    public void setPackageLength(int packageLength) {
        this.packageLength = packageLength;
    }

    /**
     * 毛重
     *
     * 包括装箱的重量（单位：克）。
     */
    public int getPackageWeight() {
        return packageWeight;
    }

    public void setPackageWeight(int packageWeight) {
        this.packageWeight = packageWeight;
    }

    /**
     * 净重
     *
     * 不包括装箱的重量（单位：克）。
     */
    public int getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(int netWeight) {
        this.netWeight = netWeight;
    }

    /**
     * 属性
     *
     * 物料附加的属性列表。
     *
     * （此属性不可索引）
     */
    @OneToMany(mappedBy = "material", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<MaterialAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<MaterialAttribute> attributes) {
        if (attributes == null)
            throw new NullPointerException("attributes");
        this.attributes = attributes;
    }

    public String getAttribute(String name) {
        if (name == null)
            throw new NullPointerException("name");
        for (MaterialAttribute attribute : attributes)
            if (name.equals(attribute.getName()))
                return attribute.getValue();
        return null;
    }

    public MaterialAttribute setAttribute(String name, String value) {
        if (name == null)
            throw new NullPointerException("name");
        if (value == null)
            throw new NullPointerException("value");
        for (MaterialAttribute attribute : attributes)
            if (name.equals(attribute.getName())) {
                attribute.setValue(value);
                return attribute;
            }
        MaterialAttribute attribute = new MaterialAttribute(this, name, value);
        attributes.add(attribute);
        return attribute;
    }

    /**
     * 附件
     *
     * 附加的相关文件列表。
     */
    @ElementCollection
    @JoinTable(name = "MaterialAttachment", //
    /*        */inverseJoinColumns = @JoinColumn(name = "userFile"))
    public List<UserFile> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<UserFile> attachments) {
        if (attachments == null)
            throw new NullPointerException("attachments");
        this.attachments = attachments;
    }

    /**
     * 仓库选项
     *
     * 物料对应各个仓库的选项。
     */
    @OneToMany(mappedBy = "material", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<MaterialWarehouseOption> getOptions() {
        return options;
    }

    public void setOptions(List<MaterialWarehouseOption> options) {
        this.options = options;
    }

    public MaterialWarehouseOption getOption(StockWarehouse warehouse) {
        if (warehouse == null)
            throw new NullPointerException("warehouse");
        for (MaterialWarehouseOption option : options) {
            if (option.getWarehouse().equals(warehouse))
                return option;
        }
        MaterialWarehouseOption option = new MaterialWarehouseOption();
        option.setWarehouse(warehouse);
        option.setMaterial(this);
        options.add(option);
        return option;
    }

    /**
     * 预设库位
     *
     * 物料入库时的推荐库位。
     */
    @OneToMany(mappedBy = "material", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<MaterialPreferredLocation> getPreferredLocations() {
        return preferredLocations;
    }

    public void setPreferredLocations(List<MaterialPreferredLocation> preferredLocations) {
        this.preferredLocations = preferredLocations;
    }

    public void addPreferredLocation(StockLocation preferredLocation) {
        this.preferredLocations.add(new MaterialPreferredLocation(this, preferredLocation));
    }

    /**
     * 物料价格
     *
     * 物料的我方报价。
     *
     * Get the prices list, the latest price the first.
     *
     * @return Non-<code>null</code> price list.
     */
    @OneToMany(mappedBy = "material", orphanRemoval = true)
    @OrderBy("date DESC")
    @Cascade(CascadeType.ALL)
    public List<MaterialPrice> getPrices() {
        return prices;
    }

    /**
     * Set the prices list.
     *
     * @param prices
     *            Price list. Should have been sorted in descend order by date.
     */
    public void setPrices(List<MaterialPrice> prices) {
        if (prices == null)
            throw new NullPointerException("prices");
        this.prices = prices;
    }

    public void addPrice(Date date, BigDecimal price) {
        MaterialPrice entry = new MaterialPrice();
        entry.setMaterial(this);
        entry.setDate(date);
        entry.setPrice(new MCValue(price));
        prices.add(entry);
    }

    /**
     * 最新价格
     *
     * 物料最近一次我方的报价。
     *
     * Get the latest material price.
     *
     * @return The latest material. <code>null</code> if there isn't any material price defined.
     */
    @Redundant
    @Transient
    public MaterialPrice getLatestPrice() {
        if (prices.isEmpty())
            return null;
        MaterialPrice first = prices.get(0);
        return first;
    }

    /**
     * Add the latest material price to the price list.
     *
     * @param price
     *            Non-<code>null</code> material price.
     */
    public void addLatestPrice(MaterialPrice price) {
        if (price == null)
            throw new NullPointerException("price");
        prices.add(0, price);
    }

}
