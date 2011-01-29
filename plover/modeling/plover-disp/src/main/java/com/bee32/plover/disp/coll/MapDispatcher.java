package com.bee32.plover.disp.coll;

import java.util.Map;

import com.bee32.plover.disp.AbstractDispatcher;
import com.bee32.plover.disp.DispatchConfig;
import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.util.ITokenQueue;

public class MapDispatcher
        extends AbstractDispatcher {

    public MapDispatcher() {
        super();
    }

    public MapDispatcher(String name) {
        super(name);
    }

    @Override
    public int getOrder() {
        return DispatchConfig.COLL_ORDER;
    }

    @Override
    public Object dispatch(Object context, ITokenQueue tokens)
            throws DispatchException {
        if (!(context instanceof Map<?, ?>))
            return null;

        String key = tokens.shift();
        if (key == null)
            return null;

        Map<?, ?> map = (Map<?, ?>) context;
        if (!map.containsKey(key))
            return null;

        Object value = map.get(key);
        return value;
    }

}
