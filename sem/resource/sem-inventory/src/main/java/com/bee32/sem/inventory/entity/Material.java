package com.bee32.sem.inventory.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
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
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.DummyId;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.cache.Redundant;
import com.bee32.sem.file.entity.UserFile;
import com.bee32.sem.world.thing.Thing;

/**
 * 物料
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "material_seq", allocationSize = 1)
public class Material
        extends Thing<MaterialXP> {

    private static final long serialVersionUID = 1L;

    MaterialCategory category;

    String serial;
    String barCode;
    String modelSpec;

    List<MaterialAttribute> attributes = new ArrayList<MaterialAttribute>();
    List<UserFile> attachments = new ArrayList<UserFile>();

    List<MaterialWarehouseOption> options = new ArrayList<MaterialWarehouseOption>();
    List<MaterialPreferredLocation> preferredLocations = new ArrayList<MaterialPreferredLocation>();

    List<MaterialPrice> prices = new ArrayList<MaterialPrice>();

    // ------------------------------------------------------------------------
    // 需要索引的常用的物料属性（这些属性和单位还算无关）。
    //
    String color;

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

    public Material(String name, String serial) {
        super(name);
        if (serial == null)
            throw new NullPointerException("serial");
        this.serial = serial;
    }

    /**
     * 物料类别
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
     * 物品编码、物品序列号
     *
     * XXX - 必填/自然键？
     */
    @NaturalId(mutable = true)
    @Column(length = 32)
    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        if (serial == null)
            throw new NullPointerException("serial");
        this.serial = serial;
    }

    /**
     * 物品条码
     */
    @Column(length = 30)
    public String getBarCode() {
        return barCode;
    }

    /**
     * 物品条码
     */
    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    /**
     * 规格型号
     */
    @Column(length = 30)
    public String getModelSpec() {
        return modelSpec;
    }

    public void setModelSpec(String modelSpec) {
        this.modelSpec = modelSpec;
    }

    /**
     * 颜色
     */
    @Column(length = 10)
    String getColor() {
        return color;
    }

    void setColor(String color) {
        this.color = color;
    }

    /**
     * 装箱宽度（单位：厘米）
     */
    public int getPackageWidth() {
        return packageWidth;
    }

    public void setPackageWidth(int packageWidth) {
        this.packageWidth = packageWidth;
    }

    /**
     * 装箱高度（单位：厘米）
     */
    public int getPackageHeight() {
        return packageHeight;
    }

    public void setPackageHeight(int packageHeight) {
        this.packageHeight = packageHeight;
    }

    /**
     * 装箱长度（单位：厘米）
     */
    public int getPackageLength() {
        return packageLength;
    }

    public void setPackageLength(int packageLength) {
        this.packageLength = packageLength;
    }

    /**
     * 毛重（单位：克）
     */
    public int getPackageWeight() {
        return packageWeight;
    }

    public void setPackageWeight(int packageWeight) {
        this.packageWeight = packageWeight;
    }

    /**
     * 净重（单位：克）
     */
    public int getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(int netWeight) {
        this.netWeight = netWeight;
    }

    /**
     * 物料附加的属性。
     *
     * （此属性不可索引）
     */
    @OneToMany(mappedBy = "material")
    @Cascade(CascadeType.ALL)
    public List<MaterialAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<MaterialAttribute> attributes) {
        if (attributes == null)
            throw new NullPointerException("attributes");
        this.attributes = attributes;
    }

    /**
     * 附件文件。
     */
    @CollectionOfElements
    @Cascade(CascadeType.ALL)
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

    @OneToMany(mappedBy = "material")
    @Cascade(CascadeType.ALL)
    public List<MaterialWarehouseOption> getOptions() {
        return options;
    }

    public void setOptions(List<MaterialWarehouseOption> options) {
        this.options = options;
    }

    @OneToMany(mappedBy = "material")
    @Cascade(CascadeType.ALL)
    public List<MaterialPreferredLocation> getPreferredLocations() {
        return preferredLocations;
    }

    public void setPreferredLocations(List<MaterialPreferredLocation> preferredLocations) {
        this.preferredLocations = preferredLocations;
    }

    /**
     * Get the prices list, the latest price the first.
     *
     * @return Non-<code>null</code> price list.
     */
    @OneToMany(mappedBy = "material")
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

    /**
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

    @Override
    protected Serializable naturalId() {
        if (serial == null)
            return new DummyId(this);
        else
            return serial;
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        return new Equals(prefix + "serial", serial);
    }

}
