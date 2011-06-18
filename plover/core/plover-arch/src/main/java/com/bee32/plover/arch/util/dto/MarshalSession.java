package com.bee32.plover.arch.util.dto;

import java.util.HashMap;
import java.util.Map;

public class MarshalSession
        implements IMarshalSession {

    final Object context;

    Map<MarshalKey, Object> marshalledMap;

    Map<BaseDto<?, ?>, Object> unmarshalled;

    public MarshalSession(Object context) {
        this.context = context;
    }

    @Override
    public Object getContext() {
        if (context == null)
            throw new IllegalStateException("No marshal context.");
        return context;
    }

    final Map<MarshalKey, Object> getMarshalledMap() {
        if (marshalledMap == null) {
            synchronized (this) {
                if (marshalledMap == null) {
                    marshalledMap = new HashMap<MarshalKey, Object>();
                }
            }
        }
        return marshalledMap;
    }

    final Map<BaseDto<?, ?>, Object> getUnmarshalledMap() {
        if (unmarshalled == null) {
            synchronized (this) {
                if (unmarshalled == null) {
                    unmarshalled = new HashMap<BaseDto<?, ?>, Object>();
                }
            }
        }
        return unmarshalled;
    }

    @Override
    public <S, D extends BaseDto<?, ?>> //
    D getMarshalled(S source, MarshalType marshalType, int selection) {
        MarshalKey marshalKey = new MarshalKey(source, marshalType, selection);
        return (D) getMarshalledMap().get(marshalKey);
    }

    @Override
    public <S, D extends BaseDto<?, ?>> //
    void addMarshalled(S source, MarshalType marshalType, int selection, D dest) {
        MarshalKey marshalKey = new MarshalKey(source, marshalType, selection);
        getMarshalledMap().put(marshalKey, dest);
    }

    @Override
    public <S, D extends BaseDto<?, ?>> S getUnmarshalled(D dto) {
        return (S) getUnmarshalledMap().get(dto);
    }

    @Override
    public <S, D extends BaseDto<?, ?>> void addUnmarshalled(D dest, S source) {
        getUnmarshalledMap().put(dest, source);
    }

}

class MarshalKey {

    Object source;
    MarshalType mt;
    int selection;

    public MarshalKey(Object source, MarshalType mt, int selection) {
        this.source = source;
        this.mt = mt;
        this.selection = selection;
    }

    @Override
    public int hashCode() {
        int hash = mt.hashCode();
        hash += System.identityHashCode(source);
        hash += selection;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        MarshalKey o = (MarshalKey) obj;
        if (mt != o.mt)
            return false;
        if (source != o.source)
            return false;
        if (selection != o.selection)
            return false;
        return true;
    }

}
