package com.bee32.plover.cache.pull;

public class NullableRef<T>
        implements Ref<T> {

    private boolean isSet;
    private T value;

    public NullableRef() {
    }

    public NullableRef(T value) {
        this.value = value;
        this.isSet = true;
    }

    @Override
    public boolean isSet() {
        return isSet;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public synchronized void set(T value) {
        this.value = value;
        this.isSet = true;
    }

    public synchronized void clear() {
        this.value = null;
        this.isSet = false;
    }

}
