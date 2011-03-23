package com.bee32.plover.arch;

import java.lang.ref.WeakReference;

public abstract class WeakFactory<T> {

    private WeakReference<T> ref;

    public T get() {
        T val;
        if (ref != null) {
            val = ref.get();
            if (val != null)
                return val;
        }
        val = create();
        ref = new WeakReference<T>(val);
        return val;
    }

    protected abstract T create();

}
