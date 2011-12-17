package com.bee32.plover.arch.util.dto;

public interface IPropertyAccessor<T> {

    Class<? extends T> getType();

    T get(Object obj);

    void set(Object obj, T value);

}
