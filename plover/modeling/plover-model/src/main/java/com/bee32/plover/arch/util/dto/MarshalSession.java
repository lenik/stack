package com.bee32.plover.arch.util.dto;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import javax.free.IllegalUsageException;

public class MarshalSession
        implements IMarshalSession {

    final Object context;

    // Entity+key => DTO
    Map<Object, Object> marshalledMap;

    // DTO => Entity
    Map<Object, Object> unmarshalledMap;

    public MarshalSession(Object context) {
        this.context = context;
    }

    @Override
    public Object getContext() {
        if (context == null)
            throw new IllegalStateException("No marshal context.");
        return context;
    }

    final Map<Object, Object> getMarshalledMap() {
        if (marshalledMap == null) {
            synchronized (this) {
                if (marshalledMap == null) {
                    marshalledMap = new HashMap<Object, Object>();
                }
            }
        }
        return marshalledMap;
    }

    final Map<Object, Object> getUnmarshalledMap() {
        if (unmarshalledMap == null) {
            synchronized (this) {
                if (unmarshalledMap == null) {
                    unmarshalledMap = new IdentityHashMap<Object, Object>();
                }
            }
        }
        return unmarshalledMap;
    }

    final void checkTyped(BaseDto_Skel<?, ?> dto) {
        if (!dto.isStereotyped())
            throw new IllegalUsageException("Dto hasn't been stereotyped, yet: " + dto);
    }

    @Override
    public <D> D getMarshalled(Object marshalKey) {
        return (D) getMarshalledMap().get(marshalKey);
    }

    @Override
    public void addMarshalled(Object marshalKey, BaseDto_Skel<?, ?> dto) {
        checkTyped(dto);
        getMarshalledMap().put(marshalKey, dto);
    }

    @Override
    public <S> S getUnmarshalled(BaseDto_Skel<?, ?> dto) {
        return (S) getUnmarshalledMap().get(dto);
    }

    @Override
    public void addUnmarshalled(BaseDto_Skel<?, ?> dto, Object source) {
        checkTyped(dto);
        Map<Object, Object> map = getUnmarshalledMap();
        map.put(dto, source);
    }

}

class MarshalKey {

    Object source;
    MarshalType mt;
    int fmask;

    public MarshalKey(Object source, MarshalType mt, int fmask) {
        this.source = source;
        this.mt = mt;
        this.fmask = fmask;
    }

    @Override
    public int hashCode() {
        int hash = mt.hashCode();
        hash += System.identityHashCode(source);
        hash += fmask;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        MarshalKey o = (MarshalKey) obj;
        if (mt != o.mt)
            return false;
        if (source != o.source)
            return false;
        if (fmask != o.fmask)
            return false;
        return true;
    }

}
