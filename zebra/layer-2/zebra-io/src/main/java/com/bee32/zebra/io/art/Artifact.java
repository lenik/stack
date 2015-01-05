package com.bee32.zebra.io.art;

import java.util.HashMap;
import java.util.Map;

import net.bodz.bas.repr.form.meta.FormInput;
import net.bodz.bas.repr.form.meta.NumericInput;
import net.bodz.bas.repr.form.meta.OfGroup;
import net.bodz.bas.repr.form.meta.StdGroup;

import com.bee32.zebra.oa.OaGroups;
import com.tinylily.model.base.CoEntity;
import com.tinylily.model.base.IId;

public class Artifact
        extends CoEntity
        implements IId<Integer> {

    private static final long serialVersionUID = 1L;

    public static final int N_SKU_CODE = 30;
    public static final int N_BAR_CODE = 30;
    public static final int N_UOM_PROPERTY = 20;
    public static final int N_SPEC = 100;
    public static final int N_COLOR = 20;
    public static final int N_CAUTION = 100;

    private int id;
    private ArtifactCategory category;
    private String skuCode;
    private String barCode;

    private UOM uom = UOMs.PIECE;
    private String uomProperty = "数量";
    private Map<UOM, Double> convMap = new HashMap<UOM, Double>();
    private int decimalDigits = 2;

    private String spec;
    private String color;
    private String caution;

    private final Dim3d bbox = new Dim3d();
    private double weight;
    private double netWeight;

    private SupplyMethod supplyMethod = SupplyMethod.BUY;
    private int supplyDelay; // in days

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 分类
     */
    @OfGroup(StdGroup.Classification.class)
    public ArtifactCategory getCategory() {
        return category;
    }

    public void setCategory(ArtifactCategory category) {
        this.category = category;
    }

    /**
     * 存货识别码。
     * 
     * @label SKU
     * @placeholder 输入存货识别码
     */
    @OfGroup(StdGroup.Identity.class)
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    /**
     * @placeholder 输入条形码
     */
    @OfGroup({ StdGroup.Identity.class, OaGroups.Packaging.class })
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    /**
     * 单位
     */
    @OfGroup(OaGroups.Packaging.class)
    public UOM getUom() {
        return uom;
    }

    public void setUom(UOM uom) {
        this.uom = uom;
    }

    /**
     * 度量属性
     * 
     * @placeholder 输入衡量单位的用途属性，如"长度"、"重量"
     */
    @OfGroup(OaGroups.Packaging.class)
    public String getUomProperty() {
        return uomProperty;
    }

    public void setUomProperty(String uomProperty) {
        this.uomProperty = uomProperty;
    }

    /**
     * 单位转换表
     */
    @OfGroup(OaGroups.Packaging.class)
    public Map<UOM, Double> getConvMap() {
        return convMap;
    }

    public void setConvMap(Map<UOM, Double> convMap) {
        if (convMap == null)
            throw new NullPointerException("convMap");
        this.convMap = convMap;
    }

    /**
     * 小数位数
     */
    @OfGroup(StdGroup.Settings.class)
    @FormInput(textWidth = 3)
    @NumericInput(min = 0, max = 10)
    public int getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    /**
     * 规格型号
     * 
     * @placeholder 输入规格/型号
     */
    @OfGroup(StdGroup.Identity.class)
    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    /**
     * 颜色
     * 
     * @placeholder 输入颜色名称
     */
    @OfGroup(OaGroups.ColorManagement.class)
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    /**
     * 警告提示
     * 
     * @placeholder 输入警告信息，如危险品、易碎、易爆炸等
     */
    public String getCaution() {
        return caution;
    }

    public void setCaution(String caution) {
        this.caution = caution;
    }

    /**
     * 装箱尺寸
     */
    @OfGroup(OaGroups.Packaging.class)
    public Dim3d getBbox() {
        return bbox;
    }

    /**
     * 毛重
     */
    @OfGroup(OaGroups.Packaging.class)
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * 净重
     */
    @OfGroup(OaGroups.Packaging.class)
    public double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(double netWeight) {
        this.netWeight = netWeight;
    }

    /**
     * 供应方法
     */
    @OfGroup(StdGroup.Schedule.class)
    public SupplyMethod getSupplyMethod() {
        return supplyMethod;
    }

    public void setSupplyMethod(SupplyMethod supplyMethod) {
        if (supplyMethod == null)
            throw new NullPointerException("supplyMethod");
        this.supplyMethod = supplyMethod;
    }

    /**
     * 供应延时
     */
    @OfGroup(StdGroup.Schedule.class)
    public int getSupplyDelay() {
        return supplyDelay;
    }

    public void setSupplyDelay(int supplyDelay) {
        this.supplyDelay = supplyDelay;
    }

}
