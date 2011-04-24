package com.bee32.plover.arch.util;

public interface IDataTransferObject<T, C> {

    /**
     * Marshal the given source object into this object.
     *
     * <p>
     * This is the same to {@link #marshal(Object, boolean)} with <code>checkCyclicReferences</code>
     * set to <code>false</code>.
     *
     * @param source
     *            Source object to be marshalled, maybe <code>null</code>.
     * @return The marshalled object. it may be this, or flyweight, or <code>null</code> if the
     *         specified <code>source</code> object is <code>null</code>.
     */
    <D extends DataTransferObject<T, ?>> D marshal(T source);

    /**
     * Marshal the given source object into this object, with cyclic references checked and avoided.
     *
     * @param source
     *            Source object to be marshalled, maybe <code>null</code>.
     * @return The marshalled object. it may be this, or flyweight, or <code>null</code> if the
     *         specified <code>source</code> object is <code>null</code>.
     */
    <D extends DataTransferObject<T, ?>> D marshalRec(T source);

    /**
     * Create a new entity bean and populate with properties defined in this object.
     *
     * @return Non-<code>null</code> new created entity bean.
     */
    T unmarshal();

    /**
     * Populate the specified target entity bean with properties defined in this object.
     *
     * @param target
     *            Non-<code>null</code> target entity bean.
     */
    void unmarshalTo(T target);

    T unmarshal(C context);

    void unmarshalTo(C context, T target);

}
