package com.bee32.zebra.oa.hr;

import java.math.BigDecimal;

/**
 * 雇员技能等级
 *
 * <p lang="en">
 * Person Skill Category Level
 */
public class JobSkillCategoryLevel {

    JobSkillCategory category;
    int score;
    String label;
    BigDecimal bonus = BigDecimal.ZERO;

    public JobSkillCategory getCategory() {
        return category;
    }

    public void setCategory(JobSkillCategory category) {
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

    /**
     * 技能等级系数
     *
     * 用于计算工资的技能等级系数。
     */
    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

}
