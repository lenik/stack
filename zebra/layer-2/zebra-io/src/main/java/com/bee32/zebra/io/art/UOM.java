package com.bee32.zebra.io.art;

import com.tinylily.model.base.CoCode;

/**
 * 度量单位
 * 
 * 衡量单位的标准定义。
 * 
 * <p lang="en">
 * Unit Of Measurement
 */
public class UOM
        extends CoCode<UOM> {

    private static final long serialVersionUID = 1L;

    public static final int N_CODE = 20;
    public static final int N_PROPERTY = 20;

    private double scale;
    private String property = "数量";

    public UOM() {
    }

    public UOM(String code, String label, String property) {
        this(code, label, Double.NaN, null, property);
    }

    public UOM(String code, String label, double scale, UOM stdRef) {
        this(code, label, scale, stdRef, stdRef.property);
    }

    public UOM(String code, String label, double scale, UOM stdRef, String property) {
        if (code == null)
            throw new NullPointerException("code");
        if (label == null)
            throw new NullPointerException("label");
        if (property == null)
            throw new NullPointerException("property");
        setCode(code);
        setLabel(label);
        setParent(stdRef);
        this.scale = scale;
        this.property = property;
    }

    public void setParentId(int parentId) {
        UOM parent = new UOM();
        parent.setId(parentId);
        setParent(parent);
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

}
