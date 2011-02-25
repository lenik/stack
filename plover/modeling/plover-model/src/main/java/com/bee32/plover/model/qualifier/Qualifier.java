package com.bee32.plover.model.qualifier;

import java.io.Serializable;

import javax.free.Nullables;

import com.bee32.plover.arch.Component;

public abstract class Qualifier<Q extends Qualifier<Q>>
        extends Component
        implements Comparable<Q>, Serializable {

    private static final long serialVersionUID = 1L;

    protected final Class<Q> qualifierType;

    public Qualifier(Class<Q> qualifierType) {
        this(qualifierType, null);
    }

    /**
     * Construct the qualifier with a given qualifier name.
     *
     * The qualifier name is not the same as qualifier type name.
     */
    public Qualifier(Class<Q> qualifierType, String name) {
        super(name);
        if (qualifierType == null)
            throw new NullPointerException("qualifierType");
        this.qualifierType = qualifierType;
    }

    /**
     * Get the qualifier type.
     */
    public Class<Q> getQualifierType() {
        return qualifierType;
    }

    public String getQualifierTypeName() {
        return qualifierType.getSimpleName();
    }

    /**
     * Get the priority of the qualifier.
     *
     * @return Priority in integer.
     */
    public int getQualifierPriority() {
        return 0;
    }

    public int getPriority() {
        return 0;
    }

    protected transient final int typeHash = getClass().hashCode();

    /**
     * Qualifier must implement the hash code in efficient way.
     *
     * @return The hash code.
     */
    @Override
    public/* final */int hashCode() {
        int hash = typeHash;
        String name = getName();
        if (name != null)
            hash += name.hashCode();
        return hash + hashCodeSpecific();
    }

    protected abstract int hashCodeSpecific();

    /**
     * This method should be treated as <b>final</b>.
     */
    @Override
    public/* final */boolean equals(Object obj) {
        if (!qualifierType.isInstance(obj))
            return false;

        Q o = qualifierType.cast(obj);

        if (!Nullables.equals(getName(), o.getName()))
            return false;

        return equalsSpecific(o);
    }

    /**
     * Test if the this qualifier is equals to the another.
     *
     * @param qualifier
     *            Non-<code>null</code> qualifier to compare with.
     */
    public abstract boolean equalsSpecific(Q o);

    /**
     * Compare to another qualifier.
     *
     * @param o
     *            Non-<code>null</code> qualifier. Using <code>null</code> qualifier is meaningless.
     */
    @Override
    public int compareTo(Q o) {
        QualifierComparator qcmp = QualifierComparator.getInstance();
        int cmp = qcmp.compare(this, o);
        if (cmp != 0)
            return cmp;
        return compareSpecific(o);
    }

    /**
     * The compare method, assert that qualifier-priority & priority are the same.
     */
    public abstract int compareSpecific(Q o);

}
