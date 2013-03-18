package com.bee32.sem.hr.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.hr.util.ScoreLevelMap;

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
 * 字段 {@link #getDescription() description} 中描述获得技能的附加信息。
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "person_skill_seq", allocationSize = 1)
public class PersonSkill
        extends UIEntityAuto<Integer>
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    EmployeeInfo employeeInfo;

    PersonSkillCategory category;
    int score;
    Date date;
    BigDecimal bonus = BigDecimal.ZERO;

    @Override
    public void populate(Object source) {
        if (source instanceof PersonSkill)
            _populate((PersonSkill) source);
        else
            super.populate(source);
    }

    protected void _populate(PersonSkill o) {
        super._populate(o);
        employeeInfo = o.employeeInfo;
        category = o.category;
        score = o.score;
        date = o.date;
        bonus = o.bonus;
    }

    /**
     * 雇员
     *
     * 相关的雇员。
     */
    @ManyToOne
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
    @ManyToOne
    public PersonSkillCategory getCategory() {
        return category;
    }

    public void setCategory(PersonSkillCategory category) {
        this.category = category;
    }

    /**
     * 评分
     *
     * 量化的技能评分。
     *
     * @see PersonSkillCategory#getLevelMap()
     */
    @Column(nullable = false)
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
    @Temporal(TemporalType.TIMESTAMP)
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
    @Column(precision = MONEY_ITEM_PRECISION, scale = MONEY_ITEM_SCALE)
    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

}
