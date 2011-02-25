package com.bee32.plover.model.qualifier;

public interface IQualified {

    /**
     * Get all qualifiers in order.
     *
     * @return Non-<code>null</code> qualifiers iterable which is already orderred.
     * @see QualifierComparator
     */
    Iterable<Qualifier<?>> getQualifiers();

    /**
     * Get all qualifiers of specific type.
     *
     * @return Non-<code>null</code> qualifiers iterable for the specific type.
     */
    <Q extends Qualifier<Q>> Iterable<Q> getQualifiers(Class<Q> qualifierType);

    /**
     * Get the first qualifier if there's any.
     *
     * @return The qualifier of specific type. Returns <code>null</code> if there isn't any.
     */
    <Q extends Qualifier<Q>> Q getQualifier(Class<Q> qualifierType);

}
