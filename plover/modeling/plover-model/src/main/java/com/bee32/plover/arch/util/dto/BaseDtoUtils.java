package com.bee32.plover.arch.util.dto;

import java.io.Serializable;

import javax.free.ParseException;

import com.bee32.plover.arch.type.FriendTypes;
import com.bee32.plover.arch.util.TextMap;

public class BaseDtoUtils {

    static DummyDto dummy = new DummyDto();

    static <S, D extends BaseDto<S>> D mar(int fmask, S source) {
        Class<S> sourceType = (Class<S>) source.getClass();
        Class<D> dtoType;
        try {
            dtoType = (Class<D>) FriendTypes.getFriendType(sourceType, "dto");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return dummy.marshal(dtoType, fmask, source);
    }

    @SuppressWarnings("unchecked")
    static <S, D extends BaseDto<S>> D mar(S source) {
        return (D) mar(Fmask.F_MORE, source);
    }

}

class Dummy {
}

class DummyDto
        extends BaseDto<Dummy> {

    private static final long serialVersionUID = 1L;

    @Override
    protected void _marshal(Dummy source) {
    }

    @Override
    protected void _unmarshalTo(Dummy target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    @Override
    public boolean isNullRef() {
        return false;
    }

    @Override
    public <self_t extends BaseDto<?>> self_t ref(Dummy source) {
        return null;
    }

    @Override
    protected Dummy mergeDeref(Dummy given) {
        return null;
    }

    @Override
    public Serializable getKey() {
        return null;
    }

    @Override
    protected boolean idEquals(BaseDto<Dummy> other) {
        return false;
    }

    @Override
    protected int idHashCode() {
        return 0;
    }

}