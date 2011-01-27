package com.bee32.plover.stateflow;

import java.io.Serializable;

import com.bee32.plover.model.Model;

public abstract class State
        extends Model
        implements /* IIndexable, */Serializable {

    private static final long serialVersionUID = 1L;

    public void accept(IStateVisitor stateVisitor) {
        // Do nothing by default.
    }

}
