package com.bee32.sem.hr.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.free.DecodeException;

import com.bee32.plover.ox1.dict.SimpleNameDictDto;
import com.bee32.sem.hr.entity.PersonSkillCategory;
import com.bee32.sem.hr.util.ScoreLevelMap;

public class PersonSkillCategoryDto
        extends SimpleNameDictDto<PersonSkillCategory> {
    private static final long serialVersionUID = 1L;

    String levelData;
    List<PersonSkillCategoryLevelDto> levelList;

    @Override
    protected void _marshal(PersonSkillCategory source) {
        super._marshal(source);
        this.levelData = source.getLevelsData();
        this.levelList = convertMapToList(source.getLevelMap());
    }

    @Override
    protected void _unmarshalTo(PersonSkillCategory target) {
        super._unmarshalTo(target);
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
            level.setLabel(map.get(score));
            levelList.add(level);
        }
        return levelList;
    }

    ScoreLevelMap convertListToMap(List<PersonSkillCategoryLevelDto> levelList) {
        ScoreLevelMap slm = new ScoreLevelMap();
        for (PersonSkillCategoryLevelDto level : levelList) {
            slm.put(level.getScore(), level.getLabel());
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

}
