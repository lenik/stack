package com.bee32.zebra.tk.stat;

import net.bodz.lily.model.base.CoObject;

public class ValueDistrib
        extends CoObject {

    private static final long serialVersionUID = 1L;

    String value;
    String valueLabel;
    String valueDescription;
    long count;
    double sum1;
    double sum2;

    public ValueDistrib() {
    }

    @Override
    public Object getId() {
        return value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueLabel() {
        return valueLabel;
    }

    public void setValueLabel(String valueLabel) {
        this.valueLabel = valueLabel;
    }

    public String getValueDescription() {
        return valueDescription;
    }

    public void setValueDescription(String valueDescription) {
        this.valueDescription = valueDescription;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getSum1() {
        return sum1;
    }

    public void setSum1(double sum1) {
        this.sum1 = sum1;
    }

    public double getSum2() {
        return sum2;
    }

    public void setSum2(double sum2) {
        this.sum2 = sum2;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append(value);
        sb.append(": ");
        sb.append(count);
        return sb.toString();
    }

}
