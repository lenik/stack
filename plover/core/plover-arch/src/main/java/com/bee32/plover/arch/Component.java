package com.bee32.plover.arch;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.WeakHashMap;

import javax.free.Strings;

import com.bee32.plover.arch.ui.Appearance;
import com.bee32.plover.arch.ui.IAppearance;
import com.bee32.plover.arch.util.ExceptionSupport;
import com.bee32.plover.arch.util.VirtualOrigin;

public abstract class Component
        implements IComponent {

    /**
     * This should be final field, but make it non-final to overcome the java language restriction.
     * <p>
     * Avoid to change the name field, except in the constructor.
     */
    protected final/* final */String name;

    private IAppearance appearance;
    private ExceptionSupport exceptionSupport;

    public Component(String name) {
        this.name = name;
    }

    static transient Map<Class<?>, String> simpleNames;

    public Component() {
        Class<? extends Component> componentClass = getClass();

        if (simpleNames == null)
            simpleNames = new WeakHashMap<Class<?>, String>();

        String simpleName = simpleNames.get(componentClass);
        if (simpleName == null) {
            Class<?> origin = getOrigin(componentClass);
            String originName = origin.getSimpleName();
            if (originName.startsWith("Abstract"))
                originName = originName.substring("Abstract".length());

            simpleName = componentClass.getSimpleName();
            if (simpleName.endsWith(originName))
                simpleName = simpleName.substring(0, simpleName.length() - originName.length());
        }

        this.name = Strings.hyphenatize(simpleName);
    }

    static Class<?> getOrigin(Class<?> clazz) {
        while (clazz != null) {

            if (Modifier.isAbstract(clazz.getModifiers()))
                return clazz;

            for (Annotation annotation : clazz.getDeclaredAnnotations())
                if (annotation instanceof VirtualOrigin)
                    return clazz;

            clazz = clazz.getSuperclass();
        }
        return clazz;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IAppearance getAppearance() {
        if (appearance == null) {
            Class<?> componentClass = getClass();
            appearance = new Appearance(componentClass);
        }
        return appearance;
    }

    @Override
    public synchronized ExceptionSupport getExceptionSupport() {
        if (exceptionSupport == null)
            exceptionSupport = new ExceptionSupport();
        return exceptionSupport;
    }

    protected final void _throw(Exception e) {
        getExceptionSupport().throwException(e);
    }

    protected final boolean recover(Exception e) {
        return getExceptionSupport().recoverException(this, e);
    }

    @Override
    public String toString() {
        return getName();
    }

}
