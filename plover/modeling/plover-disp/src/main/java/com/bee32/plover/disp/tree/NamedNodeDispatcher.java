package com.bee32.plover.disp.tree;

import com.bee32.plover.arch.naming.INamedNode;
import com.bee32.plover.disp.AbstractDispatcher;
import com.bee32.plover.disp.DispatchConfig;
import com.bee32.plover.disp.DispatchContext;
import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.IDispatchContext;
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
        return DispatchConfig.REPOSITORY_ORDER;
    }

    @Override
    public IDispatchContext dispatch(IDispatchContext context, ITokenQueue tokens)
            throws DispatchException {
        Object obj = context.getObject();

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
        return new DispatchContext(context, result, key);
    }

}
