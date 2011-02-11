package com.bee32.plover.disp.dyna;

import com.bee32.plover.disp.AbstractDispatcher;
import com.bee32.plover.disp.DispatchConfig;
import com.bee32.plover.disp.DispatchException;
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
    public Object dispatch(Object context, ITokenQueue tokens)
            throws DispatchException {
        if (!(context instanceof IDispatchable))
            return null;

        IDispatchable dispatchable = (IDispatchable) context;
        return dispatchable.dispatch(context, tokens);
    }

}
