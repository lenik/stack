package com.bee32.plover.inject.bridge;

import com.bee32.plover.inject.IContainer;

public class SpringDIBridge {

    public static <T> T load(IContainer container, Class<T> objType) {
        throw new UnsupportedOperationException("not implemented");
    }

    public static <T> T load(IContainer container, Class<T> objType, Object qualifier) {
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * 注入到 springBean。
     */
    public static void autowire(IContainer container, Object bean) {
    }

}
