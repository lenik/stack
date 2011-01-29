package com.bee32.plover.disp;

import com.bee32.plover.arch.Component;

public abstract class AbstractDispatcher
        extends Component
        implements IDispatcher {

    public AbstractDispatcher() {
        super();
    }

    public AbstractDispatcher(String name) {
        super(name);
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
