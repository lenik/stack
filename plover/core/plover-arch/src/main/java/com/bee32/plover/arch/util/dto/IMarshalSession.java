package com.bee32.plover.arch.util.dto;

public interface IMarshalSession {

    Object getContext();

    <D> D getMarshalled(Object marshalKey);

    void addMarshalled(Object marshalKey, Object dto);

    <S> S getUnmarshalled(Object dto);

    void addUnmarshalled(Object dto, Object source);

}
