package com.bee32.sem.hr.entity;

import java.math.BigDecimal;

import javax.persistence.Column;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.ox1.config.DecimalConfig;

public class PersonSkillCategoryLevel
        extends EntityAuto<Integer>
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    PersonSkillCategory category;
    int score;
    String label;
    BigDecimal bonus = BigDecimal.ZERO;

    public PersonSkillCategory getCategory() {
        return category;
    }

    public void setCategory(PersonSkillCategory category) {
        this.category = category;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Column(precision = MONEY_ITEM_PRECISION, scale = MONEY_ITEM_SCALE)
    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

}
