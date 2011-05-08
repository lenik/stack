package com.bee32.plover.arch.util;

import java.util.IdentityHashMap;
import java.util.Map;

public class MarshalSession<C>
        implements IMarshalSession<C> {

    final C context;

    Map<Object, Object> marshalledMap;
    Map<Object, Object> unmarshalled;

    public MarshalSession(C context) {
        if (context == null)
            throw new NullPointerException("context");
        this.context = context;
    }

    @Override
    public C getContext() {
        return context;
    }

    final Map<Object, Object> getMarshalledMap() {
        if (marshalledMap == null) {
            synchronized (this) {
                if (marshalledMap == null) {
                    marshalledMap = new IdentityHashMap<Object, Object>();
                }
            }
        }
        return marshalledMap;
    }

    final Map<Object, Object> getUnmarshalledMap() {
        if (unmarshalled == null) {
            synchronized (this) {
                if (unmarshalled == null) {
                    unmarshalled = new IdentityHashMap<Object, Object>();
                }
            }
        }
        return unmarshalled;
    }

    @Override
    public <S, D> D getMarshalled(S source) {
        return (D) getMarshalledMap().get(source);
    }

    @Override
    public <S, D> void addMarshalled(S source, D dest) {
        getMarshalledMap().put(source, dest);
    }

    @Override
    public <S, D> S getUnmarshalled(D dto) {
        return (S) getUnmarshalledMap().get(dto);
    }

    @Override
    public <S, D> void addUnmarshalled(D dest, S source) {
        getUnmarshalledMap().put(dest, source);
    }

}
