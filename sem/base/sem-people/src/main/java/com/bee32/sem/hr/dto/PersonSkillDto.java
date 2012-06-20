package com.bee32.sem.hr.dto;

import java.util.Date;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.hr.entity.PersonSkill;

public class PersonSkillDto
        extends UIEntityDto<PersonSkill, Long> {

    private static final long serialVersionUID = 1L;

    EmployeeInfoDto employeeInfo;
    PersonSkillCategoryDto category;
    int score;
    Date date;

    @Override
    protected void _marshal(PersonSkill source) {
        employeeInfo = mref(EmployeeInfoDto.class, source.getEmployeeInfo());
        category = mref(PersonSkillCategoryDto.class, source.getCategory());
        score = source.getScore();
        date = source.getDate();
    }

    @Override
    protected void _unmarshalTo(PersonSkill target) {
        merge(target, "employeeInfo", employeeInfo);
        merge(target, "category", category);
        target.setScore(score);
        target.setDate(date);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public EmployeeInfoDto getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(EmployeeInfoDto employeeInfo) {
        this.employeeInfo = employeeInfo;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLevelLabel(int score){
        return category.getLevel(score).getLabel();
    }
}
