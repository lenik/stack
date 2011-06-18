package com.bee32.plover.arch.util.dto;

import java.util.Map;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;

public abstract class DataExchangeObject<T>
        extends BaseDto<T, Void> {

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
    protected abstract void _parse(TextMap map)
            throws ParseException;

    @Override
    protected abstract void _export(Map<String, Object> map);

}
