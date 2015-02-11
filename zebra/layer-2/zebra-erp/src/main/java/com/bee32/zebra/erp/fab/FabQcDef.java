package com.bee32.zebra.erp.fab;

import com.tinylily.model.base.CoCode;

/**
 * 质检参数
 */
public class FabQcDef
        extends CoCode<FabQcDef> {

    private static final long serialVersionUID = 1L;

    boolean optional;
    double min;
    double max;

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

}
