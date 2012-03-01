package com.bee32.plover.arch.util.dto;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.free.IllegalUsageException;

public class MarshalSession
        implements IMarshalSession {

    // Entity+key => DTO
    Map<Object, Object> marshalledMap;

    // DTO => Entity
    Map<Object, Object> unmarshalledMap;

    final Map<Object, Object> getMarshalledMap() {
        if (marshalledMap == null) {
            synchronized (this) {
                if (marshalledMap == null) {
                    marshalledMap = new HashMap<Object, Object>(); // <Object, Object>();
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

    final void checkTyped(BaseDto_Skel<?> dto) {
        if (!dto.isStereotyped())
            throw new IllegalUsageException("Dto hasn't been stereotyped, yet: " + dto);
    }

    @Override
    public <D> D getMarshalled(Object marshalKey) {
        return (D) getMarshalledMap().get(marshalKey);
    }

    @Override
    public void addMarshalled(Object marshalKey, BaseDto_Skel<?> dto) {
        checkTyped(dto);
        getMarshalledMap().put(marshalKey, dto);
    }

    @Override
    public <S> S getUnmarshalled(BaseDto_Skel<?> dto) {
        return (S) getUnmarshalledMap().get(dto);
    }

    @Override
    public void addUnmarshalled(BaseDto_Skel<?> dto, Object source) {
        checkTyped(dto);
        Map<Object, Object> map = getUnmarshalledMap();
        map.put(dto, source);
    }

    public String getUnmarshalledContent() {
        Map<Object, Object> map = getUnmarshalledMap();
        StringBuilder sb = new StringBuilder(map.size() * 1000);
        for (Entry<?, ?> entry : map.entrySet()) {
            BaseDto<?> dto = (BaseDto<?>) entry.getKey();
            Object ent = entry.getValue();
            if (sb.length() != 0)
                sb.append('\n');
            sb.append(dto.getClass().getSimpleName() + ":" + dto.getPrimaryKey());
            sb.append(" => ");
            sb.append(ent.getClass().getSimpleName());
        }
        return sb.toString();
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
        int hash =0;
//        hash += mt.hashCode();
        hash += System.identityHashCode(source);
//        hash += fmask;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        MarshalKey o = (MarshalKey) obj;
//        if (mt != o.mt)
//            return false;
        if (source != o.source)
            return false;
//        if (fmask != o.fmask)
//            return false;
        return true;
    }

}
