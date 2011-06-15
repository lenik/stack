package com.bee32.plover.arch.util;

public interface IMarshalSession<C> {

    C getContext();

    <S, D> D getMarshalled(S source, MarshalType marshalType, int selection);

    <S, D> void addMarshalled(S source, MarshalType marshalType, int selection, D dest);

    <S, D> S getUnmarshalled(D dto);

    <S, D> void addUnmarshalled(D dest, S source);

}
