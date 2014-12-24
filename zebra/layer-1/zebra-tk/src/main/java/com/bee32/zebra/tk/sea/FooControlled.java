package com.bee32.zebra.tk.sea;

import com.tinylily.model.base.CoMomentInterval;

public abstract class FooControlled
        extends CoMomentInterval {

    private static final long serialVersionUID = 1L;

    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
