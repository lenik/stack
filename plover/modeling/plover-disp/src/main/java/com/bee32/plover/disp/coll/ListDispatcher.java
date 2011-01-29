package com.bee32.plover.disp.coll;

import java.util.List;

import com.bee32.plover.disp.AbstractDispatcher;
import com.bee32.plover.disp.DispatchConfig;
import com.bee32.plover.disp.DispatchException;
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
        return DispatchConfig.COLL_ORDER;
    }

    @Override
    public Object dispatch(Object context, ITokenQueue tokens)
            throws DispatchException {
        if (!(context instanceof List<?>))
            return null;

        Integer index = tokens.shiftInt();
        if (index == null)
            return null;

        List<?> list = (List<?>) context;
        if (index >= list.size())
            throw new DispatchException("Index out of range: " + index);

        return list.get(index);
    }

}
