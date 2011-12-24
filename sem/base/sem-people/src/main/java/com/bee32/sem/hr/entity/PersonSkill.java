package com.bee32.sem.hr.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.sem.hr.util.ScoreLevelMap;

/**
 * 员工所具有的技能
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
@SequenceGenerator(name = "idgen", sequenceName = "skill_seq", allocationSize = 1)
public class PersonSkill
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    EmployeeInfo employeeInfo;

    PersonSkillCategory category;
    int score;
    Date date;

    @ManyToOne
    public PersonSkillCategory getCategory() {
        return category;
    }

    public void setCategory(PersonSkillCategory category) {
        this.category = category;
    }

    /**
     * 量化的技能评分
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
        String levelName = levelMap.getTarget(score);
        return levelName;
    }

}
