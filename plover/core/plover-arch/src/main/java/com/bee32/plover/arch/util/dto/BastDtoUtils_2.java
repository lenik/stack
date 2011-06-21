package com.bee32.plover.arch.util.dto;

import com.bee32.plover.arch.type.FriendTypes;

public class BastDtoUtils_2 {

    static <S, D extends BaseDto<S, C>, C> D mar(IMarshalSession session, int selection, S source) {
        Class<S> sourceType = (Class<S>) source.getClass();
        Class<D> dtoType;
        try {
            dtoType = (Class<D>) FriendTypes.getFriendType(sourceType, "dto");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return BaseDto_S2.marshal(session, dtoType, selection, source);
    }

    @SuppressWarnings("unchecked")
    static <S, D extends BaseDto<S, C>, C> D mar(IMarshalSession session, S source) {
        return (D) mar(session, -1, source);
    }

    @SuppressWarnings("unchecked")
    static <S, D extends BaseDto<S, C>, C> D mar(int selection, S source) {
        return (D) mar(null, selection, source);
    }

    @SuppressWarnings("unchecked")
    static <S, D extends BaseDto<S, C>, C> D mar(S source) {
        return (D) mar(null, -1, source);
    }

}
