package com.bee32.sem.hr.util;

import java.math.BigDecimal;

public final class ScoreLevel {

    public static final int LABEL_LENGTH = 30;

    String label;
    BigDecimal bonus;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

}
