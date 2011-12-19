package com.bee32.sem.people.entity.personnel;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.NameDict;

/**
 * 职称字典类
 *
 */
@Entity
public class TitleTagname extends NameDict {

    private static final long serialVersionUID = 1L;

    public TitleTagname(String name, String label) {
        super(name, label, "");
    }


    public TitleTagname(String name, String label, String desc) {
        super(name, label, desc);
    }

}
