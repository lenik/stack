package com.bee32.plover.model.service;

import com.bee32.plover.disp.AbstractDispatcher;
import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.util.IArrival;
import com.bee32.plover.disp.util.ITokenQueue;
import com.bee32.plover.model.stage.IModelStage;

public class StageDispatcher
        extends AbstractDispatcher {

    public StageDispatcher() {
        super();
    }

    public StageDispatcher(String name) {
        super(name);
    }

    @Override
    public IArrival dispatch(IArrival context, ITokenQueue tokens)
            throws DispatchException {
        Object obj = context.getTarget();

        if (!(obj instanceof IModelStage))
            return null;

        String key = tokens.shift();
        if (key == null)
            return null;

        // IModelStage stage = (IModelStage) obj;
        // stage.contains(....);
        return null;
    }

}
