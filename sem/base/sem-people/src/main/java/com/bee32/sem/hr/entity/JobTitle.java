package com.bee32.sem.hr.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.plover.ox1.dict.NameDict;

/**
 * 职称字典类
 */
@Entity
public class JobTitle
        extends NameDict
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    BigDecimal bonus = new BigDecimal(0);

    public JobTitle() {
        super();
    }

    public JobTitle(String name, String label, BigDecimal bonus) {
        super(name, label);
        this.bonus = bonus;
    }

    public JobTitle(String name, String label, String description) {
        super(name, label, description);
    }

    @Column(precision = MONEY_ITEM_PRECISION, scale = MONEY_ITEM_SCALE)
    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

}
