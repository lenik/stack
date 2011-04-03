package com.bee32.plover.disp.dyna;

import com.bee32.plover.disp.AbstractDispatcher;
import com.bee32.plover.disp.DispatchConfig;
import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.IArrival;
import com.bee32.plover.disp.IDispatchable;
import com.bee32.plover.disp.util.ITokenQueue;

public class DynamicDispatcher
        extends AbstractDispatcher {

    public DynamicDispatcher() {
        super();
    }

    public DynamicDispatcher(String name) {
        super(name);
    }

    @Override
    public int getOrder() {
        return DispatchConfig.DYNAMIC_ORDER;
    }

    @Override
    public IArrival dispatch(IArrival context, ITokenQueue tokens)
            throws DispatchException {
        Object obj = context.getTarget();
        if (!(obj instanceof IDispatchable))
            return null;

        IDispatchable dispatchable = (IDispatchable) obj;
        return dispatchable.dispatch(context, tokens);
    }

}
