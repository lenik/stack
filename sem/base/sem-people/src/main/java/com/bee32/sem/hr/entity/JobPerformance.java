package com.bee32.sem.hr.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.plover.ox1.dict.NameDict;

/**
 * 工作表现字典类
 */
@Entity
public class JobPerformance
        extends NameDict
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    BigDecimal bonus = BigDecimal.ZERO;

    public JobPerformance() {
        super();
    }

    public JobPerformance(String name, String label) {
        super(name, label);
    }

    public JobPerformance(String name, String label, String description) {
        super(name, label, description);
    }

    @Override
    public void populate(Object source) {
        if (source instanceof JobPerformance)
            _populate((JobPerformance) source);
        else
            super.populate(source);
    }

    protected void _populate(JobPerformance o) {
        super._populate(o);
    }

    @Column(precision = MONEY_ITEM_PRECISION, scale = MONEY_ITEM_SCALE)
    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }
}
