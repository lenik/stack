package com.bee32.plover.ox1.dict;

public class IdKeyMapper<T>
        implements IKeyMapper<T, T> {

    @Override
    public T getKey(T obj) {
        return obj;
    }

    public static <T> IdKeyMapper<T> getInstance() {
        return new IdKeyMapper<T>();
    }

}
