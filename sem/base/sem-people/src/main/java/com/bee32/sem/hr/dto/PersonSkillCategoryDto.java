package com.bee32.sem.hr.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.free.DecodeException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.hr.entity.PersonSkillCategory;
import com.bee32.sem.hr.util.ScoreLevel;
import com.bee32.sem.hr.util.ScoreLevelMap;

public class PersonSkillCategoryDto
        extends UIEntityDto<PersonSkillCategory, Integer> {
    private static final long serialVersionUID = 1L;

    String levelData;
    List<PersonSkillCategoryLevelDto> levelList = new ArrayList<PersonSkillCategoryLevelDto>();

    @Override
    protected void _marshal(PersonSkillCategory source) {
        this.levelData = source.getLevelsData();
        this.levelList = convertMapToList(source.getLevelMap());
    }

    @Override
    protected void _unmarshalTo(PersonSkillCategory target) {
        try {
            String levelData = convertListToMap(getLevelList()).encode();
            target.setLevelsData(levelData);
        } catch (DecodeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    List<PersonSkillCategoryLevelDto> convertMapToList(ScoreLevelMap map) {
        List<PersonSkillCategoryLevelDto> levelList = new ArrayList<PersonSkillCategoryLevelDto>();
        for (int score : map.keySet()) {
            PersonSkillCategoryLevelDto level = new PersonSkillCategoryLevelDto();
            level.setCategory(this);
            level.setScore(score);
            level.setLabel(map.get(score).getLabel());
            level.setBonus(map.get(score).getBonus());
            levelList.add(level);
        }
        return levelList;
    }

    ScoreLevelMap convertListToMap(List<PersonSkillCategoryLevelDto> levelList) {
        ScoreLevelMap slm = new ScoreLevelMap();
        for (PersonSkillCategoryLevelDto level : levelList) {
            ScoreLevel scoreLevel = new ScoreLevel();
            scoreLevel.setLabel(level.getLabel());
            scoreLevel.setBonus(level.getBonus());
            slm.put(level.getScore(), scoreLevel);
        }
        return slm;
    }

    public void removeLevelItem(PersonSkillCategoryLevelDto level) {
        List<PersonSkillCategoryLevelDto> levelList = getLevelList();
        Iterator<PersonSkillCategoryLevelDto> iter = levelList.iterator();
        while (iter.hasNext()) {
            PersonSkillCategoryLevelDto levelDto = iter.next();
            if (level.getScore() == levelDto.getScore())
                iter.remove();
        }
    }

    public String getLevelData() {
        return levelData;
    }

    public List<PersonSkillCategoryLevelDto> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<PersonSkillCategoryLevelDto> levelList) {
        this.levelList = levelList;
    }

    public PersonSkillCategoryLevelDto getLevel(int score) {
        for (PersonSkillCategoryLevelDto level : levelList) {
            if (level.getScore() == score)
                return level;
        }
        return null;
    }

    public String getOutlineData() {
        StringBuffer tmp = new StringBuffer();
        for (PersonSkillCategoryLevelDto level : levelList) {
            if (tmp.length() == 0)
                tmp.append(",");
            tmp.append(level.getScore());
            tmp.append(":");
            tmp.append(level.getLabel());
        }
        return tmp.toString();
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

}
