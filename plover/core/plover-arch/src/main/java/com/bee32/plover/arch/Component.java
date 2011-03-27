package com.bee32.plover.arch;

import javax.free.Nullables;

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
     * The name is readable/writable to support entity design.
     *
     * <strike>
     * <p>
     * This should be final field, but make it non-final to overcome the java language restriction.
     * <p>
     * Avoid to change the name field, except in the constructor. </strike>
     */
    protected String name;

    IAppearance appearance;
    ExceptionSupport exceptionSupport;

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
            appearance = Appearance.getAppearance(componentClass);
        }
        return appearance;
    }

    /**
     * Change the appearance of this component.
     *
     * @param appearance
     *            The new appearance. Specify <code>null</code> to restore the default appearance.
     */
    void setAppearance(IAppearance appearance) {
        this.appearance = appearance;
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

    /**
     * Warning: You should override {@link #equalsSpecific(Component)} instead.
     */
    @Override
    public/* final */boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        Component other = (Component) obj;
        if (!Nullables.equals(name, other.name))
            return false;

        return equalsSpecific(other);
    }

    /**
     * Test if two components of same type are equal.
     *
     * @param obj
     *            The other object of the same class of this, to be compared.
     * @return <code>true</code> If equals.
     */
    protected boolean equalsSpecific(Component obj) {
        return false;
    }

    protected transient final int typeHash = getClass().hashCode();

    /**
     * Warning: You should override {@link #hashCodeSpecific()} instead.
     */
    @Override
    public/* final */int hashCode() {
        int hash = typeHash;
        if (name != null)
            hash += name.hashCode();

        hash += hashCodeSpecific();

        return hash;
    }

    /**
     * Get the hash code of the local part.
     *
     * @return hash code of the local part.
     */
    protected int hashCodeSpecific() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return getName();
    }

}
