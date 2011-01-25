package com.bee32.plover.cache.pull;

public interface Ref<T> {

    boolean isSet();

    T get();

    void set(T value);

}
