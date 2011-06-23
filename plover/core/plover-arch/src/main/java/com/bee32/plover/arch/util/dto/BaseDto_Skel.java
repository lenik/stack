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
 * <li>Skeleton
 * <li>Context wrappers
 * </ul>
 */
abstract class BaseDto_Skel<S, C>
        extends BaseDto_ASM<S, C> {

    private static final long serialVersionUID = 1L;

    protected MarshalType marshalType = MarshalType.SELECTION;
    protected boolean _null;

    public MarshalType getMarshalType() {
        return marshalType;
    }

    public void marshalAs(MarshalType marshalType) {
        if (marshalType == null)
            throw new NullPointerException("marshalType");
        this.marshalType = marshalType;
    }

    public boolean isNull() {
        return _null;
    }

    public void setNull(boolean _null) {
        this._null = _null;
    }

    /**
     * A reference DTO without a key is a null-ref.
     *
     * Generally, this should return:
     *
     * <pre>
     * marshalType.isReference() &amp;&amp; id == null
     * </pre>
     *
     * @return <code>true</code> If a non-<code>null</code> key is defined.
     */
    public abstract boolean isNullRef();

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
    @Override
    final <D extends BaseDto<S, C>> D marshalImpl(S source) {
        @SuppressWarnings("unchecked")
        D _this = (D) this;

        if (marshalType.isReference())
            throw new IllegalUsageException("You can't marshal into a referenced DTO.");

        if (source == null) {
            // if (!marshalType.isReference())
            // throw new IllegalUsageException("You can't marshal a null to referenced-DTO.");

            _null = true;

        } else {
            // Do the real marshal work.
            // logger.debug("marshal begin");

            IMarshalSession session = getSession();
            Object marshalKey = getMarshalKey(source);
            D marshalled = session.getMarshalled(marshalKey);
            if (marshalled != null)
                return (D) marshalled;
            session.addMarshalled(marshalKey, this);

            __marshal(source);
            _marshal(source);

            // logger.debug("marshal end");
        }

        return _this;
    }

    static class BaseKey {

        final Object source;
        final MarshalType marshalType;

        public BaseKey(Object source, MarshalType marshalType) {
            if (source == null)
                throw new NullPointerException("source");
            if (marshalType == null)
                throw new NullPointerException("marshalType");
            this.source = source;
            this.marshalType = marshalType;
        }

        @Override
        public int hashCode() {
            int hash = 0;
            hash += source.hashCode();
            hash += marshalType.hashCode();
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            BaseKey o = (BaseKey) obj;
            if (!source.equals(o.source))
                return false;
            if (marshalType != o.marshalType)
                return false;
            return true;
        }

    }

    protected Object getMarshalKey(S source) {
        return new BaseKey(source, marshalType);
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

    // @Override
    // public abstract <D extends BaseDto<S, C>> D ref(S source);

    @Override
    final S mergeImpl(S target) {
        if (isNullRef())
            return null;

        S deref = mergeDeref(target);

        if (target == null || /* TODO Check this later */target == deref) {
            IMarshalSession session = getSession();
            S unmarshalled = session.getUnmarshalled(this);
            if (unmarshalled != null)
                return unmarshalled;
            session.addUnmarshalled(this, deref);
        }

        if (marshalType.isReference()) {
            // assert deref.id == this.id.

        } else {
            switch (marshalType) {
            case SELECTION:
                __unmarshalTo(deref);
                _unmarshalTo(deref);
                break;

            // default:
            // throw new IllegalUsageException("merge from a non-selection dto is meaningless.");
            }
        }

        return deref;
    }

    /**
     * Generally, this deals with null-merge (unmarshal) and nonnull-merge (unmarshalTo).
     *
     * @param given
     *            It's most common to see <code>null</code> or an id-matching entity.
     */
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

    @Override
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
