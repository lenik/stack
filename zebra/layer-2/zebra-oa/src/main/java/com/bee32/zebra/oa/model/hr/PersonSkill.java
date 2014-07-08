package com.bee32.zebra.oa.model.hr;

import java.beans.Transient;
import java.math.BigDecimal;
import java.util.Date;

import com.tinylily.model.base.CoEntity;

/**
 * 雇员技能
 *
 * 列出员工所具有的技能。
 *
 * 这里：
 * <ul>
 * <li>技能分类必须为预先定义，没有”其他技能“这一选项。
 * <li>技能的级别必须预先定义，如英语3级、4级，不能有”其它级别“这一选项。
 * </ul>
 *
 *
 * <p lang="en">
 * Person Skill
 *
 * @rewrite description 描述获得技能的附加信息。
 */
public class PersonSkill
        extends CoEntity {

    private static final long serialVersionUID = 1L;

    EmployeeInfo employeeInfo;

    JobSkillCategory category;
    int score;
    Date date;
    BigDecimal bonus = BigDecimal.ZERO;

    /**
     * 雇员
     *
     * 相关的雇员。
     */
    public EmployeeInfo getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(EmployeeInfo employeeInfo) {
        this.employeeInfo = employeeInfo;
    }

    /**
     * 技能种类
     *
     * 雇员掌握的技能分类。
     */
    public JobSkillCategory getCategory() {
        return category;
    }

    public void setCategory(JobSkillCategory category) {
        this.category = category;
    }

    /**
     * 评分
     *
     * 量化的技能评分。
     *
     * @see JobSkillCategory#getLevelMap()
     */
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * 技能等级名称（在 {@link #getCategory() category }中定义等级列表。）
     */
    @Transient
    public String getLevel() {
        ScoreLevelMap levelMap = category.getLevelMap();
        String levelName = levelMap.getTarget(score).getLabel();
        return levelName;
    }

    /**
     * 技术获得日期
     *
     * 如技能证书的颁发日期。
     */
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * 技能系数
     *
     * 用于计算工资的技能系数。
     */
    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

}
