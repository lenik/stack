package com.bee32.zebra.oa.cloudfs;

import com.tinylily.model.base.CoEntity;

public class CloudFileSpace
        extends CoEntity {

    private static final long serialVersionUID = 1L;

    int id;
    long capacity;
    long remaining;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
