package com.bee32.plover.arch.util;

import java.util.IdentityHashMap;
import java.util.Map;

public class MarshalSession<C>
        implements IMarshalSession<C> {

    final C context;

    Map<MarshalKey, Object> marshalledMap;

    Map<Object, Object> unmarshalled;

    public MarshalSession(C context) {
        this.context = context;
    }

    @Override
    public C getContext() {
        if (context == null)
            throw new IllegalStateException("No marshal context.");
        return context;
    }

    final Map<MarshalKey, Object> getMarshalledMap() {
        if (marshalledMap == null) {
            synchronized (this) {
                if (marshalledMap == null) {
                    marshalledMap = new IdentityHashMap<MarshalKey, Object>();
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
    public <S, D> D getMarshalled(S source, MarshalType marshalType, int selection) {
        MarshalKey marshalKey = new MarshalKey(source, marshalType, selection);
        return (D) getMarshalledMap().get(marshalKey);
    }

    @Override
    public <S, D> void addMarshalled(S source, MarshalType marshalType, int selection, D dest) {
        MarshalKey marshalKey = new MarshalKey(source, marshalType, selection);
        getMarshalledMap().put(marshalKey, dest);
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
