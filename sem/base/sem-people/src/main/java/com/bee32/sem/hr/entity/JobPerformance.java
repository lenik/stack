package com.bee32.sem.hr.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.plover.ox1.config.DecimalConfig;

/**
 * 工作表现字典类
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "job_performance_seq", allocationSize = 1)
public class JobPerformance
        extends UIEntityAuto<Integer>
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    BigDecimal bonus = BigDecimal.ZERO;

    public JobPerformance() {
        super();
    }

    public JobPerformance(String label, BigDecimal bonus) {
        super();
        this.label = label;
        this.bonus = bonus;
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
