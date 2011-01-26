package com.bee32.plover.inject.bridge;

import com.bee32.plover.inject.IContainer;

/**
 * 与 Plexus 的组件框架集成。
 */
public class PlexusComponentBridge {

    public static <T> T load(IContainer container, Class<T> objType) {
        throw new UnsupportedOperationException("not implemented");
    }

    public static <T> T load(IContainer container, Class<T> objType, Object qualifier) {
        throw new UnsupportedOperationException("not implemented");
    }

    public static void inject(IContainer container, Object component) {
    }

}
