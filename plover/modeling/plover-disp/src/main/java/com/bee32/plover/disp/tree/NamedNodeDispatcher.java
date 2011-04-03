package com.bee32.plover.disp.tree;

import com.bee32.plover.arch.naming.INamedNode;
import com.bee32.plover.disp.AbstractDispatcher;
import com.bee32.plover.disp.DispatchConfig;
import com.bee32.plover.disp.Arrival;
import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.IArrival;
import com.bee32.plover.disp.util.ITokenQueue;

public class NamedNodeDispatcher
        extends AbstractDispatcher {

    public NamedNodeDispatcher() {
        super();
    }

    public NamedNodeDispatcher(String name) {
        super(name);
    }

    @Override
    public int getOrder() {
        return DispatchConfig.ORDER_NAMED_NODE;
    }

    @Override
    public IArrival dispatch(IArrival context, ITokenQueue tokens)
            throws DispatchException {
        Object obj = context.getTarget();

        if (!(obj instanceof INamedNode))
            return null;

        String key = tokens.peek();
        if (key == null)
            return null;

        INamedNode locator = (INamedNode) obj;

        Object result = locator.getChild(key);
        if (result == null)
            return null;

        tokens.shift();
        return new Arrival(context, result, key);
    }

}
