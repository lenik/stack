package com.bee32.plover.model.service;

import com.bee32.plover.disp.AbstractDispatcher;
import com.bee32.plover.disp.DispatchConfig;
import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.util.IArrival;
import com.bee32.plover.disp.util.ITokenQueue;

public class ModelDispatcher
        extends AbstractDispatcher {

    public ModelDispatcher() {
        super();
    }

    public ModelDispatcher(String name) {
        super(name);
    }

    @Override
    public int getOrder() {
        return DispatchConfig.ORDER_MODEL;
    }

    @Override
    public IArrival dispatch(IArrival context, ITokenQueue tokens)
            throws DispatchException {
        return null;
    }

}
