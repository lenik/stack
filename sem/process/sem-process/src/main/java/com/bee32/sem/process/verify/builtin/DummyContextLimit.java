package com.bee32.sem.process.verify.builtin;

import com.bee32.sem.process.verify.DummyContext;

public class DummyContextLimit
        extends DummyContext<IContextLimit>
        implements IContextLimit {

    private String limitName;
    private long contextLimit;

    public DummyContextLimit(String limitName, long contextLimit) {
        super(IContextLimit.class);

        this.limitName = limitName;
        this.contextLimit = contextLimit;
    }

    @Override
    public String getLimitName() {
        return limitName;
    }

    @Override
    public long getContextLimit() {
        return contextLimit;
    }

}
