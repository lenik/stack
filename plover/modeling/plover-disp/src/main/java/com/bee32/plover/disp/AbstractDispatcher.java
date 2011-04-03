package com.bee32.plover.disp;

import com.bee32.plover.arch.Component;
import com.bee32.plover.disp.util.TokenQueue;

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

    @Override
    public IArrival dispatch(IArrival context, String path)
            throws DispatchException {
        TokenQueue tq = new TokenQueue(path);
        IArrival target = dispatch(context, tq);
        if (!tq.isEmpty())
            throw new DispatchException("Dispatch isn't completed: " + tq);
        return target;
    }

}
