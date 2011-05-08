package com.bee32.plover.arch.util;

import java.util.Map;

import javax.free.ParseException;

public interface IDataTransferObject<S, C> {

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
    <D extends IDataTransferObject<S, C>> D marshal(IMarshalSession<C> session, S source);

    /**
     * Populate the specified target entity bean with properties defined in this object.
     *
     * @param target
     *            Non-<code>null</code> target entity bean.
     * @return The target itself, or a new one.
     */
    S merge(IMarshalSession<C> session, S target);

    void parse(IMarshalSession<C> session, Map<String, ?> map)
            throws ParseException;

    void export(IMarshalSession<C> session, Map<String, Object> map);

}
