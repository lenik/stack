package com.bee32.plover.arch.util.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.type.FriendTypes;
import com.bee32.plover.arch.util.TextMap;

public class BastDtoUtils {

    static BaseDto<Object, Object> instance;
    static {
        instance = new BaseDto<Object, Object>() {

            private static final long serialVersionUID = 1L;

            @Override
            public <D extends BaseDto<? extends Object, Object>> D ref(Object source) {
                return null;
            }

            @Override
            public boolean isNullRef() {
                return false;
            }

            @Override
            protected void _marshal(Object source) {
            }

            @Override
            protected Object mergeDeref(Object given) {
                return null;
            }

            @Override
            protected void _unmarshalTo(Object target) {
            }

            @Override
            protected void _parse(TextMap map)
                    throws ParseException {
            }

        };
    }

    static <S, D extends BaseDto<S, C>, C> D mar(int selection, S source) {
        Class<S> sourceType = (Class<S>) source.getClass();
        Class<D> dtoType;
        try {
            dtoType = (Class<D>) FriendTypes.getFriendType(sourceType, "dto");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return instance.marshal(dtoType, selection, source);
    }

    @SuppressWarnings("unchecked")
    static <S, D extends BaseDto<S, C>, C> D mar(S source) {
        return (D) mar(-1, source);
    }

}
