package com.bee32.sem.hr.entity;

import com.bee32.plover.orm.entity.EntityAuto;

public class PersonSkillCategoryLevel
        extends EntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    PersonSkillCategory category;
    int score;
    String label;

    public PersonSkillCategory getCategory() {
        return category;
    }

    public void setCategory(PersonSkillCategory category) {
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
