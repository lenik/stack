package com.bee32.zebra.tk.sea;

import com.tinylily.model.base.CoMomentInterval;

public abstract class FooControlled
        extends CoMomentInterval {

    private static final long serialVersionUID = 1L;

    public static final int S_PRIVATE = S_INIT;
    public static final int S_SUBMIT = S_INIT + 1;
    public static final int S_PUBLIC = S_INIT + 2;
    public static final int S_USER_MIN = 1000;

    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
