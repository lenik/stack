package com.bee32.icsf.login;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.LongNameDict;

@Entity
public class PrivateQuestion
        extends LongNameDict {

    private static final long serialVersionUID = 1L;

    public PrivateQuestion() {
        super();
    }

    public PrivateQuestion(String name, String label) {
        super(name, label);
    }


    @Override
    public void populate(Object source) {
        if (source instanceof PrivateQuestion)
            _populate((PrivateQuestion) source);
        else
            super.populate(source);
    }

    protected void _populate(PrivateQuestion o) {
        super._populate(o);
    }

}
