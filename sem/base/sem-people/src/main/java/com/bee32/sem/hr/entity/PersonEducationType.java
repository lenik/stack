package com.bee32.sem.hr.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.plover.ox1.config.DecimalConfig;

/**
 * 学历字典类
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "person_education_seq", allocationSize = 1)
public class PersonEducationType
        extends UIEntityAuto<Integer>
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    BigDecimal bonus = new BigDecimal(0);

    public PersonEducationType() {
        super();
    }

    public PersonEducationType(String label, BigDecimal bonus) {
        super();
        this.label = label;
        this.bonus = bonus;
    }

    @Column(precision = MONEY_ITEM_PRECISION, scale = MONEY_ITEM_SCALE)
    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }
}
