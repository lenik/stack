package com.bee32.plover.arch;

import com.bee32.plover.arch.ui.Appearance;
import com.bee32.plover.arch.ui.IAppearance;
import com.bee32.plover.arch.util.AutoNaming;
import com.bee32.plover.arch.util.ExceptionSupport;

/**
 * A component is bound with appearance, and optionally an alternative exception support.
 */
public abstract class Component
        implements IComponent {

    /**
     * This should be final field, but make it non-final to overcome the java language restriction.
     * <p>
     * Avoid to change the name field, except in the constructor.
     */
    protected final String name;

    private IAppearance appearance;
    private ExceptionSupport exceptionSupport;

    public Component(String name) {
        this.name = name;
    }

    public Component() {
        this.name = AutoNaming.getAutoName(getClass());
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
