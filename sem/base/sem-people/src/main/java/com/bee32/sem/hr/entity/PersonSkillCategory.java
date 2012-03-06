package com.bee32.sem.hr.entity;

import javax.free.DecodeException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.bee32.plover.ox1.dict.NameDict;
import com.bee32.sem.hr.util.ScoreLevelMap;

/**
 * 员工技能字典类
 */
@Entity
public class PersonSkillCategory
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public static final int MAX_LEVELS = 20;
    public static final int LEVEL_NAME_LENGTH = 20;

    static final int LEVEL_ENTRY_LENGTH = LEVEL_NAME_LENGTH + 10;
    public static final int LEVELS_DATA_LENGTH = MAX_LEVELS * LEVEL_ENTRY_LENGTH;

    private ScoreLevelMap levelMap;

    public PersonSkillCategory() {
        super();
    }

    public PersonSkillCategory(String name, String label) {
        super(name, label);
    }

    public PersonSkillCategory(String name, String label, String description) {
        super(name, label, description);
    }

X-Population

    @Transient
    public ScoreLevelMap getLevelMap() {
        return levelMap;
    }

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
