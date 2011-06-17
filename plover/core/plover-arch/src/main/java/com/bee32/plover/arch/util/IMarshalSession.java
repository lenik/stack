package com.bee32.plover.arch.util;

public interface IMarshalSession {

    Object getContext();

    <S, D extends BaseDto<?, ?>> D getMarshalled(S source, MarshalType marshalType, int selection);

    <S, D extends BaseDto<?, ?>> void addMarshalled(S source, MarshalType marshalType, int selection, D dest);

    <S, D extends BaseDto<?, ?>> S getUnmarshalled(D dto);

    <S, D extends BaseDto<?, ?>> void addUnmarshalled(D dest, S source);

}
