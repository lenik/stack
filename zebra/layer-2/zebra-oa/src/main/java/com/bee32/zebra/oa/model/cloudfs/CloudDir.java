package com.bee32.zebra.oa.model.cloudfs;

import com.tinylily.model.base.CoEntity;

public class CloudDir
        extends CoEntity {

    private static final long serialVersionUID = 1L;

    private CloudFileSpace fileSpace;
    private CloudDir parent;

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