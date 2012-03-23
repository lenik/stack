package com.bee32.sem.make.entity;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.ShortNameDict;

/**
 * 工艺标准名称
 */
@Entity
public class MakeStepName
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    public MakeStepName() {
        super();
    }

    public MakeStepName(String name, String label, String description) {
        super(name, label, description);
    }

    public MakeStepName(String name, String label) {
        super(name, label);
    }

}
