package com.bee32.sem.api;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * value-object.
 */
public class SalaryElement
        implements Serializable {

    private static final long serialVersionUID = 1L;

    String path;
    String label;
    BigDecimal value;

    public SalaryElement() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

}
