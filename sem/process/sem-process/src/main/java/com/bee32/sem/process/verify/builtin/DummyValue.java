package com.bee32.sem.process.verify.builtin;

import com.bee32.sem.process.verify.DummyHolder;

public class DummyValue
        extends DummyHolder<IValueHolder>
        implements IValueHolder {

    private String valueDescription;
    private long value;

    public DummyValue(String valueDescription, long value) {
        super(IValueHolder.class);

        this.valueDescription = valueDescription;
        this.value = value;
    }

    @Override
    public String getValueDescription() {
        return valueDescription;
    }

    @Override
    public long getLongValue() {
        return value;
    }

}
