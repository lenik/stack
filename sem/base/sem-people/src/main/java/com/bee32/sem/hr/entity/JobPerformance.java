package com.bee32.sem.hr.entity;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.NameDict;

/**
 * 工作表现字典类
 */
@Entity
public class JobPerformance
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public JobPerformance() {
        super();
    }

    public JobPerformance(String name, String label) {
        super(name, label);
    }

    public JobPerformance(String name, String label, String description) {
        super(name, label, description);
    }
}
