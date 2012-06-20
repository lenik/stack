package com.bee32.sem.hr.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.IEnclosedObject;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.hr.entity.PersonSkillCategoryLevel;

public class PersonSkillCategoryLevelDto
        extends EntityDto<PersonSkillCategoryLevel, Integer>
        implements IEnclosedObject<PersonSkillCategoryDto> {

    private static final long serialVersionUID = 1L;
    PersonSkillCategoryDto category;
    int score;
    String label;

    public PersonSkillDto toPersonSkillCategory(EmployeeInfoDto info) {
        PersonSkillDto personSkill = new PersonSkillDto();
        personSkill.setEmployeeInfo(info);
        personSkill.setCategory(category);
        personSkill.setScore(score);
        personSkill.setDate(getCreatedDate());
        return personSkill;
    }

    @Override
    protected void _marshal(PersonSkillCategoryLevel source) {
        category = mref(PersonSkillCategoryDto.class, source.getCategory());
        score = source.getScore();
        label = source.getLabel();
    }

    @Override
    protected void _unmarshalTo(PersonSkillCategoryLevel target) {
        merge(target, "category", category);
        target.setScore(score);
        target.setLabel(label);
    }

    @Override
    protected Object clone()
            throws CloneNotSupportedException {
        return super.clone();
    }

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
