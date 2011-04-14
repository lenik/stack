package com.bee32.plover.arch.util;

import java.util.Map;

import javax.free.IVariantLookupMap;
import javax.free.NotImplementedException;

public abstract class DataExchangeObject<T>
        extends DataTransferObject<T> {

    private static final long serialVersionUID = 1L;

    @Override
    protected void _marshal(T source) {
        throw new NotImplementedException();
    }

    @Override
    protected void _unmarshalTo(T target) {
        throw new NotImplementedException();
    }

    @Override
    public abstract void parse(IVariantLookupMap<String> map);

    @Override
    public abstract void export(Map<String, Object> map);

}
