package com.bee32.plover.stateflow;

import java.io.Serializable;

public abstract class State
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public abstract IStateSchema getSchema();

    public void accept(IStateVisitor stateVisitor) {
        // Do nothing by default.
    }

}
