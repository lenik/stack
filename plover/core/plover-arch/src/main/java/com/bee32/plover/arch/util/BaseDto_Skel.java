package com.bee32.plover.arch.util;

import java.util.Map;

import javax.free.NotImplementedException;
import javax.free.ParseException;
import javax.free.TypeConvertException;

/**
 * Layer 2
 *
 * <ul>
 * <li>Skeletion
 * <li>Auto Session Management
 * <li>Context wrappers
 * </ul>
 */
abstract class BaseDto_Skel<S, C>
        extends BaseDto_VTU<S, C> {

    private static final long serialVersionUID = 1L;

    public BaseDto_Skel() {
        super();
    }

    public BaseDto_Skel(S source) {
        super(source);
    }

    public BaseDto_Skel(int selection) {
        super(selection);
    }

    public BaseDto_Skel(int selection, S source) {
        super(selection, source);
    }

    /**
     * Marshal skeleton implementation.
     */
    final <D extends IDataTransferObject<S, C>> D marshalImpl(S source) {
        @SuppressWarnings("unchecked")
        D _this = (D) this;

        if (source == null) {
            marshalType = MarshalType.ID_REF;

            return _this;
        }

        // Do the real marshal work.
        // logger.debug("marshal begin");

        __marshal(source);
        _marshal(source);

        // logger.debug("marshal end");

        marshalType = MarshalType.SELECTION;

        return _this;
    }

    /**
     * Read some internal properties from the specified source bean into this object.
     *
     * @param source
     *            Non-null source source whose properties are read into this object.
     */
    protected void __marshal(S source) {
    }

    /**
     * Read some user properties from the specified source bean into this object.
     *
     * @param source
     *            Non-null source source whose properties are read into this object.
     */
    protected abstract void _marshal(S source);

    /**
     * Write some internal properties from this object into the specified source bean.
     *
     * @param context
     *            Non-<code>null</code> unmarshal context.
     * @param target
     *            Non-<code>null</code> target source bean.
     * @throws NotImplementedException
     *             If unmarshal isn't supported for this DTO.
     */
    protected void __unmarshalTo(S target) {
    }

    /**
     * Write some user properties from this object into the specified source bean.
     *
     * @param context
     *            Non-<code>null</code> unmarshal context.
     * @param target
     *            Non-<code>null</code> target source bean.
     * @throws NotImplementedException
     *             If unmarshal isn't supported for this DTO.
     */
    protected abstract void _unmarshalTo(S target);

    final void parseImpl(TextMap map)
            throws ParseException {
        __parse(map);
        _parse(map);
        marshalType = MarshalType.SELECTION;
    }

    protected void __parse(TextMap map)
            throws ParseException {
    }

    /**
     * Parse parameters from a variant lookup map.
     *
     * @throws TypeConvertException
     *             If type conversion failure inside the variant lookup map.
     * @throws ParseException
     *             Other parse exception caused by user implementation.
     */
    protected abstract void _parse(TextMap map)
            throws ParseException;

    final void exportImpl(Map<String, Object> map) {
        __export(map);
        _export(map);
    }

    protected void __export(Map<String, Object> map) {
    }

    protected void _export(Map<String, Object> map) {
        exportProperties(map);
    }

}
