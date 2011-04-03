package com.bee32.plover.disp;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.Module;
import com.bee32.plover.disp.util.ITokenQueue;

public abstract class DispatchModule
        extends Module
        implements IDispatchable {

    private IDispatcher delegate;

    public DispatchModule() {
        super();
    }

    public DispatchModule(String name) {
        super(name);
    }

    public abstract IDispatcher getDispatcher();

    final IDispatcher loadDispatcher() {
        if (delegate == null) {
            synchronized (this) {
                if (delegate == null) {

                    delegate = getDispatcher();

                    if (delegate == null)
                        throw new IllegalUsageException("Get delegated dispatcher returns null");
                }
            }
        }
        return delegate;
    }

    @Override
    public IArrival dispatch(IArrival context, ITokenQueue tokens)
            throws DispatchException {
        return loadDispatcher().dispatch(context, tokens);
    }

    @Override
    public IArrival dispatch(IArrival context, String path)
            throws DispatchException {
        return loadDispatcher().dispatch(context, path);
    }

    @Override
    protected void preamble() {
    }

}