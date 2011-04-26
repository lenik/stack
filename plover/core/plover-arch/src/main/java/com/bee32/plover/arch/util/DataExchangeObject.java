package com.bee32.plover.arch.util;

import java.util.Map;

import javax.free.IVariantLookupMap;
import javax.free.NotImplementedException;
import javax.free.ParseException;

public abstract class DataExchangeObject<T>
        extends DataTransferObject<T, Void> {

    private static final long serialVersionUID = 1L;

    @Override
    protected void _marshal(T source) {
        throw new NotImplementedException();
    }

    @Override
    protected void _unmarshalTo(Void context, T target) {
        throw new NotImplementedException();
    }

    @Override
    public abstract void _parse(IVariantLookupMap<String> map)
            throws ParseException;

    @Override
    public abstract void export(Map<String, Object> map);

}
