package com.bee32.plover.stateflow;

import java.io.Serializable;

import com.bee32.plover.model.Model;

public abstract class State
        extends Model
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public State() {
        super();
    }

    public State(String name) {
        super(name);
    }

}
