package com.bee32.plover.disp.coll;

import java.util.List;

import com.bee32.plover.disp.AbstractDispatcher;
import com.bee32.plover.disp.DispatchConfig;
import com.bee32.plover.disp.DispatchContext;
import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.IDispatchContext;
import com.bee32.plover.disp.util.ITokenQueue;

public class ListDispatcher
        extends AbstractDispatcher {

    public ListDispatcher() {
        super();
    }

    public ListDispatcher(String name) {
        super(name);
    }

    @Override
    public int getOrder() {
        return DispatchConfig.ORDER_COLLECTION;
    }

    @Override
    public IDispatchContext dispatch(IDispatchContext context, ITokenQueue tokens)
            throws DispatchException {
        Object obj = context.getObject();

        if (!(obj instanceof List<?>))
            return null;

        String mayConsume = tokens.peek();

        Integer index = tokens.shiftInt();
        if (index == null)
            return null;

        List<?> list = (List<?>) obj;
        if (index >= list.size())
            throw new DispatchException("Index out of range: " + index);

        Object result = list.get(index);
        return new DispatchContext(context, result, mayConsume);
    }

}
