package com.bee32.plover.model.qualifier;

import javax.free.Nullables;

import com.bee32.plover.arch.Component;

public abstract class DerivedQualifier<Q extends DerivedQualifier<Q>>
        extends Qualifier<Q> {

    private static final long serialVersionUID = 1L;

    private final Q kindOf;

    /**
     * @param qualifierType
     *            This type.
     * @param name
     *            Non-<code>null</code> name. Using derived qualifier but with no name is not
     *            allowed.
     */
    public DerivedQualifier(Class<Q> qualifierType, String name) {
        this(qualifierType, name, null);
    }

    /**
     * @param qualifierType
     *            This type.
     * @param name
     *            Non-<code>null</code> name. Using derived qualifier but with no name is not
     *            allowed.
     * @param kindOf
     *            The "parent" kind of this.
     */
    public DerivedQualifier(Class<Q> qualifierType, String name, Q kindOf) {
        super(qualifierType, name);

        if (name == null)
            throw new NullPointerException("name");

        this.kindOf = kindOf;
    }

    public boolean kindOf(DerivedQualifier<?> o) {
        DerivedQualifier<?> it = this;
        while (it != null) {
            if (it.equalsSpecific(o))
                return true;
            it = it.degrade();
        }
        return false;
    }

    public Q degrade() {
        return kindOf;
    }

    @Override
    protected/* final */boolean equalsSpecific(Component obj) {
        DerivedQualifier<?> other = (DerivedQualifier<?>) obj;

        if (!Nullables.equals(name, other.name))
            return false;

        if (!Nullables.equals(kindOf, other.kindOf))
            return false;

        return equalsSpecific(other);
    }

    /**
     * Always returns <code>true</code> in the default implementation.
     * <p>
     * It's possible that using derived qualifier for strong typing, but nothing more then the
     * kind-of hierachy.
     */
    protected boolean equalsSpecific(DerivedQualifier<?> o) {
        return true;
    }

    @Override
    public int hashCode() {
        int hash = typeHash;

        String name = getName();
        if (name != null)
            hash += name.hashCode();

        if (kindOf != null)
            hash += kindOf.hashCode();

        return hash + hashCodeSpecific();
    }

    public int compareTo(Q o) {
        // Type-Priority > Qualifier-Type > Kind-Of > Instance-Priority > Other
        int cmp = DerivedQualifierComparator.getInstance().compare(this, o);
        return cmp;
    }

}
