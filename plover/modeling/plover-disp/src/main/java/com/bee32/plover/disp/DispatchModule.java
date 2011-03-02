package com.bee32.plover.disp;

import com.bee32.plover.arch.Module;
import com.bee32.plover.disp.util.ITokenQueue;

public abstract class DispatchModule
        extends Module
        implements IDispatchable {

    private IDispatcher delegate;

    public DispatchModule() {
        super();
        delegate = getDispatcher();
    }

    public DispatchModule(String name) {
        super(name);
        delegate = getDispatcher();
    }

    public abstract IDispatcher getDispatcher();

    @Override
    public IDispatchContext dispatch(IDispatchContext context, ITokenQueue tokens)
            throws DispatchException {
        return delegate.dispatch(context, tokens);
    }

    @Override
    public IDispatchContext dispatch(IDispatchContext context, String path)
            throws DispatchException {
        return delegate.dispatch(context, path);
    }

}
