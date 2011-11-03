package com.bee32.plover.inject.scope;

import org.springframework.stereotype.Component;

@Component
@PerState
public class OtherState
        implements IOtherState {

    Object other;

    @Override
    public Object getOther() {
        return other;
    }

    @Override
    public void setOther(Object other) {
        this.other = other;
    }

}
