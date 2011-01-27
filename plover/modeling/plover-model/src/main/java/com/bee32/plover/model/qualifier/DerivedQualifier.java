package com.bee32.plover.model.qualifier;

import javax.free.Nullables;

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

    public boolean kindOf(Q o) {
        DerivedQualifier<?> it = this;
        while (it != null) {
            if (it.equals(o))
                return true;
            it = it.degrade();
        }
        return false;
    }

    public Q degrade() {
        return kindOf;
    }

    @Override
    public/* final */boolean equals(Object obj) {
        if (!qualifierType.isInstance(obj))
            return false;

        Q o = qualifierType.cast(obj);

        if (!Nullables.equals(name, o.name))
            return false;

        if (!Nullables.equals(kindOf, o.kindOf))
            return false;

        return equalsSpecific(o);
    }

    /**
     * Always returns <code>true</code> in the default implementation.
     * <p>
     * It's possible that using derived qualifier for strong typing, but nothing more then the
     * kind-of hierachy.
     */
    public boolean equalsSpecific(Q o) {
        return true;
    }

    @Override
    public int hashCode() {
        int hash = typeHash;

        if (name != null)
            hash += name.hashCode();

        if (kindOf != null)
            hash += kindOf.hashCode();

        return hash + hashCodeSpecific();
    }

    /**
     * Always returns <code>0</code> in the default implementation.
     * <p>
     * It's possible that using derived qualifier for strong typing, but nothing more then the
     * kind-of hierachy.
     */
    @Override
    protected int hashCodeSpecific() {
        return 0;
    }

    /**
     * Default order by &lt;kind-of, name&gt;.
     */
    public int compareTo(Q o) {
        if (equals(o))
            return 0;

        if (this.kindOf(o))
            return 1;

        Q this_Q = qualifierType.cast(this);
        if (o.kindOf(this_Q))
            return -1;

        return name.compareTo(o.name);
    }

}
