package com.bee32.plover.disp.model;

import com.bee32.plover.disp.AbstractDispatcher;
import com.bee32.plover.disp.DispatchException;
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
    public Object dispatch(Object context, ITokenQueue tokens)
            throws DispatchException {
        if (!(context instanceof IModelStage))
            return null;

        String key = tokens.shift();
        if (key == null)
            return null;

        IModelStage stage = (IModelStage) context;
        // stage.contains(....);
        return null;
    }

}
