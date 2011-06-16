package com.bee32.plover.arch.util;

public interface IMarshalSession {

    Object getContext();

    <S, D extends DataTransferObject<?, ?>> D getMarshalled(S source, MarshalType marshalType, int selection);

    <S, D extends DataTransferObject<?, ?>> void addMarshalled(S source, MarshalType marshalType, int selection, D dest);

    <S, D extends DataTransferObject<?, ?>> S getUnmarshalled(D dto);

    <S, D extends DataTransferObject<?, ?>> void addUnmarshalled(D dest, S source);

}
