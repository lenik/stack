package com.bee32.zebra.oa.model.hr;

import net.bodz.bas.err.DecodeException;

import com.tinylily.model.base.CoEntity;

/**
 * 工作技能字典类
 * 
 * 定义员工技能和相关的等级。
 * 
 * <p lang="en">
 * Person Skill Category
 */
public class JobSkillCategory
        extends CoEntity {

    private static final long serialVersionUID = 1L;

    public static final int MAX_LEVELS = 20;
    public static final int LEVEL_NAME_LENGTH = 20;

    static final int LEVEL_ENTRY_LENGTH = LEVEL_NAME_LENGTH + 10;
    public static final int LEVELS_DATA_LENGTH = MAX_LEVELS * LEVEL_ENTRY_LENGTH;

    private ScoreLevelMap levelMap = new ScoreLevelMap();

    public JobSkillCategory() {
        super();
    }

    public JobSkillCategory(String label) {
        setLabel(label);
    }

    public JobSkillCategory(String label, String description) {
        setLabel(label);
        setDescription(description);
    }

    public ScoreLevelMap getLevelMap() {
        return levelMap;
    }

    /**
     * 等级描述
     * 
     * 一段描述各技能等级的脚本。
     */
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
