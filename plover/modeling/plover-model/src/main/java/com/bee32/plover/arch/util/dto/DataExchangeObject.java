package com.bee32.plover.arch.util.dto;

import java.io.Serializable;
import java.util.Map;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;

public abstract class DataExchangeObject<T>
        extends BaseDto<T> {

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
    public Serializable getPrimaryKey() {
        throw new UnsupportedOperationException("DXO.key is undefined.");
    }

    @Override
    public <self_t extends BaseDto<?>> self_t ref(T source) {
        throw new NotImplementedException();
    }

    @Override
    public boolean isNullRef() {
        throw new NotImplementedException();
    }

    @Override
    protected T mergeDeref(T given) {
        throw new NotImplementedException();
    }

    // TODO
    @Override
    protected boolean idEquals(BaseDto<T> other) {
        return false;
    }

    // TODO
    @Override
    protected int idHashCode() {
        return 0;
    }

    @Override
    protected abstract void _parse(TextMap map)
            throws ParseException;

    @Override
    protected abstract void _export(Map<String, Object> map);

}
