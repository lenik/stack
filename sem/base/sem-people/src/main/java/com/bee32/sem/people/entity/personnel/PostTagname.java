package com.bee32.sem.people.entity.personnel;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.NameDict;

/**
 * 职务/职位字典类
 *
 */
@Entity
public class PostTagname extends NameDict {

    private static final long serialVersionUID = 1L;

    public PostTagname(String name, String label) {
        super(name, label, "");
    }


    public PostTagname(String name, String label, String desc) {
        super(name, label, desc);
    }

}
