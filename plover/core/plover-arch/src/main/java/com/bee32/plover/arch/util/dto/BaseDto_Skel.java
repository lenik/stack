package com.bee32.plover.arch.util.dto;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;

import javax.free.IllegalUsageException;
import javax.free.NotImplementedException;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;

/**
 * Layer 2
 *
 * <ul>
 * <li>Skeletion
 * <li>Context wrappers
 * </ul>
 */
abstract class BaseDto_Skel<S, C>
        extends BaseDto_ASM<S, C> {

    private static final long serialVersionUID = 1L;

    protected MarshalType marshalType = MarshalType.SELECTION;

    public MarshalType getMarshalType() {
        return marshalType;
    }

    public void marshalAs(MarshalType marshalType) {
        if (marshalType == null)
            throw new NullPointerException("marshalType");
        this.marshalType = marshalType;
    }

    public boolean isNullRef() {
        return marshalType.isReference() && _isNullRef();
    }

    protected abstract boolean _isNullRef();

    /**
     * <pre>
     * LAYER 2
     * -----------------------------------------------------------------------
     *      This layer contains the skeleton implementations.
     *
     *      These methods are package-local.
     *      The skeleton is overall fixed in the whole project.
     * </pre>
     */

    /**
     * Marshal skeleton implementation.
     */
    final <D extends IDataTransferObject<S, C>> D marshalImpl(S source) {
        @SuppressWarnings("unchecked")
        D _this = (D) this;

        if (source == null)
            if (!marshalType.isReference())
                throw new IllegalUsageException("You can't marshal a null to referenced-DTO.");

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

    final S mergeImpl(S target) {
        if (isNullRef())
            return null;

        S deref = mergeDeref(target);

        if (marshalType == MarshalType.SELECTION) {
            __unmarshalTo(deref);
            _unmarshalTo(deref);
        }

        return deref;
    }

    protected abstract S mergeDeref(S given);

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

    protected void exportProperties(Map<String, Object> map) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(getClass());
            for (PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {
                String name = property.getName();
                Method getter = property.getReadMethod();
                Object value = getter.invoke(this);
                map.put(name, value);
            }
        } catch (Exception e) {
            map.put("exception", e);
        }
    }

}
