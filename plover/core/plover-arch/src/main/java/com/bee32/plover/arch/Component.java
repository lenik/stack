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

        Class<?> componentClass = getClass();
        this.appearance = new Appearance(componentClass);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IAppearance getAppearance() {
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
        return name;
    }

// private final transient int typeHash = getClass().hashCode();
//
// @Override
// public final int hashCode() {
// int hash = typeHash;
// if (name != null)
// hash += name.hashCode();
// return hash + hashCodeLocal();
// }
//
// public final boolean equals(Component obj) {
// Class<? extends Component> componentType = getClass();
// if (!componentType.isInstance(obj))
// return false;
// Component o = (Component) obj;
// return equalsLocal(o);
// }
//
// protected int hashCodeLocal() {
// return super.hashCode();
// }
//
// /**
// * Local equals.
// *
// * @param obj
// * The object of same type to compare.
// */
// protected boolean equalsLocal(Component obj) {
// return this == obj;
// }

}
