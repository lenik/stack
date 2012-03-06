package com.bee32.sem.hr.entity;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.NameDict;

/**
 * 职务/职位字典类
 */
@Entity
public class JobPost
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public JobPost() {
        super();
    }

    public JobPost(String name, String label) {
        super(name, label);
    }

    public JobPost(String name, String label, String description) {
        super(name, label, description);
    }

    @Override
    public void populate(Object source) {
        if (source instanceof JobPost)
            _populate((JobPost) source);
        else
            super.populate(source);
    }

    protected void _populate(JobPost o) {
        super._populate(o);
    }

}
