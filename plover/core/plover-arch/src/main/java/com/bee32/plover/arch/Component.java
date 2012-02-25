package com.bee32.plover.arch;

import java.util.Locale;

import javax.free.Nullables;

import com.bee32.plover.arch.ui.Appearance;
import com.bee32.plover.arch.ui.res.InjectedAppearance;
import com.bee32.plover.arch.util.AutoNaming;
import com.bee32.plover.arch.util.ExceptionSupport;
import com.bee32.plover.arch.util.res.ClassResourceProperties;
import com.bee32.plover.arch.util.res.IPropertyDispatcher;
import com.bee32.plover.arch.util.res.PropertyDispatcher;

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

    Appearance appearance;
    ExceptionSupport exceptionSupport;

    public Component() {
        this(null, true);
    }

    public Component(String name) {
        this(name, false);
    }

    public Component(String name, boolean autoName) {
        if (name == null && autoName)
            this.name = AutoNaming.getAutoName(getClass());
        else
            this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Appearance getAppearance() {
        if (appearance == null) {
            synchronized (this) {
                if (appearance == null) {
                    appearance = createAppearance();
                }
            }
        }
        return appearance;
    }

    Appearance createAppearance() {
        Class<?> componentClass = getClass();

        ClassResourceProperties properties = new ClassResourceProperties(componentClass, Locale.getDefault());
        IPropertyDispatcher propertyDispatcher = new PropertyDispatcher(properties);

        InjectedAppearance appearance = Appearance.prepareAppearanceCached(componentClass);

        propertyDispatcher.setRootAcceptor(appearance);

        appearance.setPropertyDispatcher(propertyDispatcher);

        return appearance;
    }

    /**
     * Change the appearance of this component.
     *
     * @param appearance
     *            The new appearance. Specify <code>null</code> to restore the default appearance.
     */
    void setAppearance(Appearance appearance) {
        if (appearance == null)
            throw new NullPointerException("appearance");
        this.appearance = appearance;
        this.appearance.setDefaultLabel(getName());
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
