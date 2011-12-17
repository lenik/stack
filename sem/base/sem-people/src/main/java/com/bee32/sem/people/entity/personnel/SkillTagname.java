package com.bee32.sem.people.entity.personnel;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.NameDict;

/**
 * 员工技能字典类
 *
 */
@Entity
public class SkillTagname extends NameDict {

    private static final long serialVersionUID = 1L;

    public SkillTagname(String name, String label) {
        super(name, label, "");
    }


    public SkillTagname(String name, String label, String desc) {
        super(name, label, desc);
    }
}
