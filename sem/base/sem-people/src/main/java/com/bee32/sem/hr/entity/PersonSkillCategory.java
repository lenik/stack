package com.bee32.sem.hr.entity;

import javax.free.DecodeException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.sem.hr.util.ScoreLevelMap;

/**
 * 雇员技能字典类
 *
 * 定义员工技能和相关的等级。
 *
 * <p lang="en">
 * Person Skill Category
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "person_skill_category_seq", allocationSize = 1)
public class PersonSkillCategory
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    public static final int MAX_LEVELS = 20;
    public static final int LEVEL_NAME_LENGTH = 20;

    static final int LEVEL_ENTRY_LENGTH = LEVEL_NAME_LENGTH + 10;
    public static final int LEVELS_DATA_LENGTH = MAX_LEVELS * LEVEL_ENTRY_LENGTH;

    private ScoreLevelMap levelMap = new ScoreLevelMap();

    public PersonSkillCategory() {
        super();
    }

    public PersonSkillCategory(String label) {
        this.label = label;
    }

    public PersonSkillCategory(String label, String description) {
        this.label = label;
        this.description = description;
    }

    @Override
    public void populate(Object source) {
        if (source instanceof PersonSkillCategory)
            _populate((PersonSkillCategory) source);
        else
            super.populate(source);
    }

    protected void _populate(PersonSkillCategory o) {
        super._populate(o);
        // levelMap.populate(o.levelMap);
    }

    @Transient
    public ScoreLevelMap getLevelMap() {
        return levelMap;
    }

    /**
     * 等级描述
     *
     * 一段描述各技能等级的脚本。
     */
    @Column(length = LEVELS_DATA_LENGTH)
    public String getLevelsData() {
        return levelMap.encode();
    }

    public void setLevelsData(String levelsData)
            throws DecodeException {
        if (levelsData == null)
            throw new NullPointerException("levelsData");
        levelMap = ScoreLevelMap.decode(levelsData);
    }

}
