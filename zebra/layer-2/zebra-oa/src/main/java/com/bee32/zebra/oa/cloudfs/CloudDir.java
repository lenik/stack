package com.bee32.zebra.oa.cloudfs;

import com.tinylily.model.base.CoEntity;

public class CloudDir
        extends CoEntity {

    private static final long serialVersionUID = 1L;

    private int id;
    private CloudFileSpace fileSpace;
    private CloudDir parent;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CloudFileSpace getFileSpace() {
        return fileSpace;
    }

    public void setFileSpace(CloudFileSpace fileSpace) {
        if (fileSpace == null)
            throw new NullPointerException("fileSpace");
        this.fileSpace = fileSpace;
    }

    public CloudDir getParent() {
        return parent;
    }

    public void setParent(CloudDir parent) {
        this.parent = parent;
    }

}