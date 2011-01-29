package com.bee32.plover.disp.model;

import com.bee32.plover.disp.AbstractDispatcher;
import com.bee32.plover.disp.DispatchConfig;
import com.bee32.plover.disp.DispatchException;
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
        return DispatchConfig.MODEL_ORDER;
    }

    @Override
    public Object dispatch(Object context, ITokenQueue tokens)
            throws DispatchException {
        return null;
    }

}
