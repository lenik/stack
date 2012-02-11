package com.bee32.plover.arch.util.dto;

public interface IMarshalSession {

    <D> D getMarshalled(Object marshalKey);

    void addMarshalled(Object marshalKey, BaseDto_Skel<?> dto);

    <S> S getUnmarshalled(BaseDto_Skel<?> dto);

    void addUnmarshalled(BaseDto_Skel<?> dto, Object source);

}
