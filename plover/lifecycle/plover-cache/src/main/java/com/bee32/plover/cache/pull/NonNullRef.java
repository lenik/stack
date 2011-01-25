package com.bee32.plover.cache.pull;

public class NonNullRef<T>
        implements Ref<T> {

    private T value;

    @Override
    public boolean isSet() {
        return value != null;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public void set(T value) {
        this.value = value;
    }

}
