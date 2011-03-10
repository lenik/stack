package com.bee32.plover.model.qualifier;

import java.io.Serializable;

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
     * @return Priority in integer, the lower value the higher priority.
     */
    public int getQualifierPriority() {
        return 0;
    }

    /**
     * Get the priority of the object.
     *
     * @return Priority in integer, the lower value the higher priority.
     */
    public int getPriority() {
        return 0;
    }

    /**
     * Compare to another qualifier.
     *
     * @param o
     *            Non-<code>null</code> qualifier. Using <code>null</code> qualifier is meaningless.
     */
    @Override
    public int compareTo(Q o) {
        // Type-Priority > Qualifier-Type > Instance-Priority > Other
        QualifierComparator qcmp = QualifierComparator.getInstance();
        return qcmp.compare(this, o);
    }

    /**
     * The compare method, assert that qualifier-priority & priority are the same.
     * <p>
     * The default implementation compares the name only.
     *
     * @return Compare result, as negative, zero, positive stand for less-than, equals,
     *         greater-than.
     */
    public int compareSpecificTo(Q o) {
        int cmp = getName().compareTo(o.getName());
        if (cmp != 0)
            return cmp;

        return 0;
    }

}
