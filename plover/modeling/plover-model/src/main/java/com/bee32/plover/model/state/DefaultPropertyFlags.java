package com.bee32.plover.model.state;

import javax.free.ReadOnlyException;

public class DefaultPropertyFlags
        extends PropertyFlags {

    @Override
    public void setBold(boolean bold) {
        throw new ReadOnlyException();
    }

    @Override
    public void setLocked(boolean locked) {
        throw new ReadOnlyException();
    }

    @Override
    public void setVisible(boolean visible) {
        throw new ReadOnlyException();
    }

    static final DefaultPropertyFlags instance = new DefaultPropertyFlags();

    public static DefaultPropertyFlags getInstance() {
        return instance;
    }

}
