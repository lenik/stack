package com.bee32.plover.arch.util;

public interface IDataTransferObject<T> {

    <D extends DataTransferObject<T>> D marshal(T source);

    T unmarshal();

    void unmarshalTo(T target);

}
