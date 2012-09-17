package com.bee32.sem.hr.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.plover.ox1.config.DecimalConfig;

/**
 * 职称字典类
 *
 * 定义了职称相关的词汇。
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "job_title_seq", allocationSize = 1)
public class JobTitle
        extends UIEntityAuto<Integer>
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    BigDecimal bonus = new BigDecimal(0);

    public JobTitle() {
        super();
    }

    public JobTitle(String label, BigDecimal bonus) {
        super();
        this.label = label;
        this.bonus = bonus;
    }

    /**
     * 职称系数
     *
     * 用于计算工资的职称系数。
     */
    @Column(precision = MONEY_ITEM_PRECISION, scale = MONEY_ITEM_SCALE)
    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

}
