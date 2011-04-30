package com.bee32.plover.arch.util;

public interface IPropertyAccessor<S, T> {

    Class<? extends T> getType();

    T get(S obj);

    void set(S obj, T value);

}
