package com.bee32.plover.disp.util;

import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.IDispatcher;

public class DispatchUtil {

    public static Object dispatch(IDispatcher dispatcher, Object startObject, ITokenQueue tokens)
            throws DispatchException {
        IArrival context = new Arrival(startObject);
        context = dispatcher.dispatch(context, tokens);
        if (context == null)
            return null;
        return context.getTarget();
    }

    public static Object dispatch(IDispatcher dispatcher, Object startObject, String path)
            throws DispatchException {
        IArrival context = new Arrival(startObject);
        context = dispatcher.dispatch(context, path);
        if (context == null)
            return null;
        return context.getTarget();
    }

}
