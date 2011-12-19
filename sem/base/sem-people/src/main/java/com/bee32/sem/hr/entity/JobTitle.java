package com.bee32.sem.hr.entity;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.NameDict;

/**
 * 职称字典类
 */
@Entity
public class JobTitle
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public JobTitle() {
        super();
    }

    public JobTitle(String name, String label) {
        super(name, label);
    }

    public JobTitle(String name, String label, String description) {
        super(name, label, description);
    }

}
