package com.bee32.sem.hr.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.plover.ox1.dict.NameDict;
import com.bee32.sem.api.ISalaryProvider;
import com.bee32.sem.api.SalaryCalcContext;
import com.bee32.sem.api.SalaryItem;

/**
 * 职务/职位字典类
 */
@Entity
public class JobPost
        extends NameDict
        implements DecimalConfig, ISalaryProvider {

    private static final long serialVersionUID = 1L;

    BigDecimal bonus = new BigDecimal(0);

    public JobPost() {
        super();
    }

    public JobPost(String name, String label, BigDecimal bonus) {
        super(name, label);
        this.bonus = bonus;
    }

    public JobPost(String name, String label, String description) {
        super(name, label, description);
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

    @Column(precision = MONEY_ITEM_PRECISION, scale = MONEY_ITEM_SCALE)
    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

    @Override
    public SalaryItem[] getSalaryItems(SalaryCalcContext ctx) {
        SalaryItem item = new SalaryItem();
        item.setPath("base.post");
        item.setLabel("职位补贴");
        item.setValue(bonus);
        return new SalaryItem[] { item };
    }
}
