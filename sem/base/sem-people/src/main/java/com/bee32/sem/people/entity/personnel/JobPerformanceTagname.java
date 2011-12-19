package com.bee32.sem.people.entity.personnel;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.NameDict;

/**
 * 工作表现字典类
 *
 */
@Entity
public class JobPerformanceTagname extends NameDict {

    private static final long serialVersionUID = 1L;

    public JobPerformanceTagname(String name, String label) {
        super(name, label, "");
    }


    public JobPerformanceTagname(String name, String label, String desc) {
        super(name, label, desc);
    }
}
