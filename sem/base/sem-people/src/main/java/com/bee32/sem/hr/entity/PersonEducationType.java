package com.bee32.sem.hr.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.plover.ox1.dict.NameDict;
import com.bee32.sem.api.ISalaryVariableProvider;
import com.bee32.sem.api.SalaryCalcContext;
import com.bee32.sem.api.SalaryElement;

/**
 * 学历字典类
 *
 */
@Entity
public class PersonEducationType
        extends NameDict
        implements DecimalConfig, ISalaryVariableProvider {

    private static final long serialVersionUID = 1L;

    BigDecimal bonus = new BigDecimal(0);

    public PersonEducationType() {
        super();
    }

    public PersonEducationType(String name, String label, BigDecimal bonus) {
        super(name, label);
        this.bonus = bonus;
    }

    public PersonEducationType(String name, String label, String desc) {
        super(name, label, desc);
    }

    @Column(precision = MONEY_ITEM_PRECISION, scale = MONEY_ITEM_SCALE)
    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

    @Override
    public SalaryElement[] getSalaryItems(SalaryCalcContext ctx) {
        SalaryElement item = new SalaryElement();
        item.setPath("base.education");
        item.setLabel("学历补贴");
        item.setValue(bonus);
        return new SalaryElement[] { item };
    }
}
