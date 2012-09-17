package com.bee32.sem.people.entity;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.NameDict;

/**
 * 联系信息分组
 *
 * 用于对多个联系信息分类，如家庭、单位等。
 */
@Entity
public class ContactCategory
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public ContactCategory() {
        super();
    }

    public ContactCategory(String name, String label) {
        super(name, label);
    }

    public ContactCategory(String name, String label, String description) {
        super(name, label, description);
    }

}
