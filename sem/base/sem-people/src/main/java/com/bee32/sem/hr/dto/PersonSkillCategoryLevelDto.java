package com.bee32.sem.hr.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.IEnclosedObject;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.dict.SimpleNameDictDto;
import com.bee32.sem.hr.entity.PersonSkillCategoryLevel;

public class PersonSkillCategoryLevelDto
        extends SimpleNameDictDto<PersonSkillCategoryLevel>
        implements IEnclosedObject<PersonSkillCategoryDto> {

    private static final long serialVersionUID = 1L;
    PersonSkillCategoryDto category;
    int score;
    String label;

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    @Override
    public PersonSkillCategoryDto getEnclosingObject() {
        return category;
    }

    @Override
    public void setEnclosingObject(PersonSkillCategoryDto enclosingObject) {
        this.category = enclosingObject;
    }

    public PersonSkillCategoryDto getCategory() {
        return category;
    }

    public void setCategory(PersonSkillCategoryDto category) {
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

}
