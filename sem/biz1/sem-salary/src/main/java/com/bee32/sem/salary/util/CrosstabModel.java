package com.bee32.sem.salary.util;

import java.io.Serializable;
import java.math.BigDecimal;

public class CrosstabModel
        implements Serializable {

    private static final long serialVersionUID = 1L;
    String personName;
    String elementDef;
    BigDecimal bonus;

    public CrosstabModel() {
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getElementDef() {
        return elementDef;
    }

    public void setElementDef(String elementDef) {
        this.elementDef = elementDef;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

}
