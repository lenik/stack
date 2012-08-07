package com.bee32.sem.wage.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.plover.ox1.dict.NameDict;
import com.bee32.sem.api.ISalaryProvider;
import com.bee32.sem.api.SalaryCalcContext;
import com.bee32.sem.api.SalaryItem;

@Entity
public class BaseBonus
        extends NameDict
        implements ISalaryProvider, DecimalConfig {

    private static final long serialVersionUID = 1L;

    boolean reward;
    BigDecimal bonus;

    public BaseBonus() {
    }

    public BaseBonus(String name, String label, boolean reward, BigDecimal bonus) {
        super(name, label);
        this.reward = reward;
        this.bonus = bonus;
    }

    public boolean isReward() {
        return reward;
    }

    public void setReward(boolean reward) {
        this.reward = reward;
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
        item.setPath("base." + name);
        item.setLabel(label);
        item.setValue(bonus);
        return new SalaryItem[] { item };
    }

}
