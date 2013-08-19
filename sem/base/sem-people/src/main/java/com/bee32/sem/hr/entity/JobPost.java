package com.bee32.sem.hr.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.plover.ox1.config.DecimalConfig;

/**
 * 岗位字典类
 *
 * 定义了公司中存在的各种工作岗位。
 *
 * <p lang="en">
 * Job Post
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "job_post_seq", allocationSize = 1)
public class JobPost
        extends UIEntityAuto<Integer>
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    BigDecimal bonus = new BigDecimal(0);

    public JobPost() {
        super();
    }

    public JobPost(String label, BigDecimal bonus) {
        super();
        this.label = label;
        this.bonus = bonus;
    }

    @Override
    public void populate(Object source) {
        if (source instanceof JobPost)
            _populate((JobPost) source);
        else
            super.populate(source);
    }

    protected void _populate(JobPost o) {
        super._populate(o);
    }

    /**
     * 岗位系数
     *
     * 用于计算工资的岗位系数。
     */
    @Column(precision = MONEY_ITEM_PRECISION, scale = MONEY_ITEM_SCALE)
    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

}
