package com.bee32.sem.process.verify;

import java.io.Serializable;

import com.bee32.plover.stateflow.MutableState;

/**
 * 审核状态/审核数据。
 */
public class VerifyState
        extends MutableState
        implements /* IIndexable, */Serializable {

    private static final long serialVersionUID = 1L;

    public VerifyState() {
        super();
    }

    public VerifyState(String name) {
        super(name);
    }

}
