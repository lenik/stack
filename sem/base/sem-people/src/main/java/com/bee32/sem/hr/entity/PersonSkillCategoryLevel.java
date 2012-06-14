package com.bee32.sem.hr.entity;

import com.bee32.plover.ox1.dict.NameDict;

public class PersonSkillCategoryLevel
        extends NameDict {

    private static final long serialVersionUID = 1L;

    int key;
    String label;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
