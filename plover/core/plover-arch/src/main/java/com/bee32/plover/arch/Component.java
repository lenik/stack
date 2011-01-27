package com.bee32.plover.arch;

import com.bee32.plover.arch.ui.Appearance;
import com.bee32.plover.arch.ui.IAppearance;

public abstract class Component
        implements IComponent {

    protected final String name;
    private IAppearance appearance;
    private ExceptionSupport exceptionSupport;

    public Component() {
        this(null);
    }

    public Component(String name) {
        this.name = name;
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
