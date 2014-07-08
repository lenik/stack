package com.bee32.zebra.oa.web;

import net.bodz.bas.repr.path.IPathArrival;
import net.bodz.bas.repr.path.IPathDispatchable;
import net.bodz.bas.repr.path.ITokenQueue;
import net.bodz.bas.repr.path.PathDispatchException;

public interface IObjectOp<T, K>
// extends IRepository<T, K>
{

    T getValue();

    void setValue(T val);

    K getId();

    void setId(K id);

    Object buildView();

}

abstract class OpImpl<T, K>
        implements IObjectOp<T, K>, IPathDispatchable {

    @Override
    public IPathArrival dispatch(IPathArrival previous, ITokenQueue tokens)
            throws PathDispatchException {
        return null;
    }

}