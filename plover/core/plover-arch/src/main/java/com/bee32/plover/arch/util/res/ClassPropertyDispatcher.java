package com.bee32.plover.arch.util.res;

/**
 * Like: PropertyDispatcher(Class_xxx.properties).
 *
 * @see PropertyDispatcher
 * @see ClassResourceProperties
 */
public class ClassPropertyDispatcher
        extends PropertyDispatcherProxy {

    public ClassPropertyDispatcher(Class<?> clazz) {
        super(classResourceDispatcher(clazz));
    }

    static IPropertyDispatcher classResourceDispatcher(Class<?> clazz) {
        IProperties properties = ClassResourceProperties.getInstance(clazz);
        return new PropertyDispatcher(properties);
    }

}
