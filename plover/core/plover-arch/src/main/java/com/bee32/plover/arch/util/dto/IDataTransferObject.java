package com.bee32.plover.arch.util.dto;

import java.io.Serializable;
import java.util.Map;

import javax.free.ParseException;

public interface IDataTransferObject<S, C>
        extends Serializable {

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
    <D extends BaseDto<? super S, C>> D marshal(IMarshalSession session, S source);

    /**
     * Turn this into a referenced-DTO.
     */
    <D extends BaseDto<? extends S, C>> D ref();

    <D extends BaseDto<? extends S, C>> D ref(S source);

    /**
     * Populate the specified target entity bean with properties defined in this object.
     *
     * @param target
     *            Non-<code>null</code> target entity bean.
     * @return The target itself, or a new one.
     */
    S merge(IMarshalSession session, S target);

    void parse(IMarshalSession session, Map<String, ?> map)
            throws ParseException;

    void export(IMarshalSession session, Map<String, Object> map);

}
