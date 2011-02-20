package com.bee32.plover.disp.plover;

import com.bee32.plover.arch.locator.IObjectLocator;
import com.bee32.plover.disp.AbstractDispatcher;
import com.bee32.plover.disp.DispatchConfig;
import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.util.ITokenQueue;

public class LocatorDispatcher
        extends AbstractDispatcher {

    public LocatorDispatcher() {
        super();
    }

    public LocatorDispatcher(String name) {
        super(name);
    }

    @Override
    public int getOrder() {
        return DispatchConfig.REPOSITORY_ORDER;
    }

    @Override
    public Object dispatch(Object context, ITokenQueue tokens)
            throws DispatchException {
        if (!(context instanceof IObjectLocator))
            return null;

        String key = tokens.peek();
        if (key == null)
            return null;

        IObjectLocator locator = (IObjectLocator) context;

        Object obj = locator.locate(key);
        if (obj == null)
            return null;

        tokens.shift();
        return obj;
    }

}
