package com.bee32.plover.disp.coll;

import java.util.Map;

import com.bee32.plover.disp.AbstractDispatcher;
import com.bee32.plover.disp.DispatchConfig;
import com.bee32.plover.disp.Arrival;
import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.IArrival;
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
        return DispatchConfig.ORDER_COLLECTION;
    }

    @Override
    public IArrival dispatch(IArrival context, ITokenQueue tokens)
            throws DispatchException {
        Object obj = context.getTarget();

        if (!(obj instanceof Map<?, ?>))
            return null;

        String key = tokens.shift();
        if (key == null)
            return null;

        Map<?, ?> map = (Map<?, ?>) obj;
        if (!map.containsKey(key))
            return null;

        Object result = map.get(key);
        return new Arrival(context, result, key);
    }

}
