package com.bee32.zebra.io.art.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.range.DoubleRange;
import net.bodz.bas.t.range.IntRange;

import com.bee32.zebra.io.art.SupplyMethod;
import com.tinylily.model.base.CoEntityCriteria;
import com.tinylily.model.sea.QVariantMap;

public class ArtifactCriteria
        extends CoEntityCriteria {

    Integer categoryId;
    String skuCode_;
    String barCode_;

    Integer uomId;
    String spec;
    String color;
    String caution;

    DoubleRange volumeRange;
    DoubleRange weightRange;
    DoubleRange netWeightRange;

    SupplyMethod supplyMethod;
    IntRange supplyDelayRange;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getSkuCodePrefix() {
        return skuCode_;
    }

    public void setSkuCode_(String skuCodePrefix) {
        this.skuCode_ = skuCodePrefix;
    }

    public String getBarCodePrefix() {
        return barCode_;
    }

    public void setBarCodePrefix(String barCodePrefix) {
        this.barCode_ = barCodePrefix;
    }

    public Integer getUomId() {
        return uomId;
    }

    public void setUomId(Integer uomId) {
        this.uomId = uomId;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCaution() {
        return caution;
    }

    public void setCaution(String caution) {
        this.caution = caution;
    }

    public DoubleRange getVolumeRange() {
        return volumeRange;
    }

    public void setVolumeRange(DoubleRange volumeRange) {
        this.volumeRange = volumeRange;
    }

    public DoubleRange getWeightRange() {
        return weightRange;
    }

    public void setWeightRange(DoubleRange weightRange) {
        this.weightRange = weightRange;
    }

    public DoubleRange getNetWeightRange() {
        return netWeightRange;
    }

    public void setNetWeightRange(DoubleRange netWeightRange) {
        this.netWeightRange = netWeightRange;
    }

    public SupplyMethod getSupplyMethod() {
        return supplyMethod;
    }

    public void setSupplyMethod(SupplyMethod supplyMethod) {
        this.supplyMethod = supplyMethod;
    }

    public IntRange getSupplyDelayRange() {
        return supplyDelayRange;
    }

    public void setSupplyDelayRange(IntRange supplyDelayRange) {
        this.supplyDelayRange = supplyDelayRange;
    }

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
        categoryId = map.getInt("cat", categoryId);
        skuCode_ = map.getString("sku", skuCode_);
        barCode_ = map.getString("bar", barCode_);
        uomId = map.getInt("uom", uomId);
        spec = map.getString("spec", spec);
        color = map.getString("spec", color);
        caution = map.getString("spec", caution);

        volumeRange = map.getDoubleRange("volumes", volumeRange);
        weightRange = map.getDoubleRange("weights", weightRange);
        netWeightRange = map.getDoubleRange("nets", netWeightRange);

        supplyMethod = map.getEnum(SupplyMethod.class, "supply", supplyMethod);
        supplyDelayRange = map.getIntRange("delays", supplyDelayRange);
    }

}
