package com.bee32.plover.arch.util.dto;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;

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
public abstract class BaseDto_Skel<S>
        extends BaseDto_ASM<S> {

    private static final long serialVersionUID = 1L;

    protected MarshalType marshalType = MarshalType.SELECTION;
    protected boolean stereotyped;

    private boolean _null;

    public MarshalType getMarshalType() {
        return marshalType;
    }

    public void _marshalAs(MarshalType marshalType) {
        if (marshalType == null)
            throw new NullPointerException("marshalType");
        this.marshalType = marshalType;
    }

    public void marshalAs(MarshalType marshalType) {
        if (marshalType == null)
            throw new NullPointerException("marshalType");

        // skip if no change.
        if (marshalType == this.marshalType)
            return;

        // prevent from change.
        if (stereotyped) {
            /**
             * 有一种情况是当新创建的 DTO 内存在引用时， marshal(P) 时，有
             *
             * P'.child'.parent' = P'.
             *
             * 此处，P' 为 selection 而 P'.child'.parent' 为 ref， 故应当允许 P'.child'.parent' 亦为 selection，当
             * unmarshal 时才可以指向同一个 P.
             *
             * 故此处仅忽略。
             */
            // throw new IllegalStateException("Can't change marshal-type after stereotyped");
            // logger.trace("stereo type changed.")
            return;
        }

        this.marshalType = marshalType;

        stereotyped = true;
    }

    public boolean isStereotyped() {
        return stereotyped;
    }

    public abstract Serializable getKey();

    public boolean isNull() {
        return _null;
    }

    public void setNull(boolean _null) {
        this._null = _null;
    }

    public final boolean isNil() {
        return isNull();
    }

    public final void setNil(boolean _nil) {
        setNull(_nil);
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

    @Override
    public <self_t extends BaseDto<?>> self_t ref() {
        @SuppressWarnings("unchecked")
        self_t self = (self_t) this;

        marshalAs(MarshalType.ID_REF);
        setNull(isNullRef());

        return self;
    }

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
    final <D extends BaseDto<? super S>> D marshalImpl(S source) {
        @SuppressWarnings("unchecked")
        D _this = (D) this;

        if (source == null) {
            // if (!marshalType.isReference())
            // throw new IllegalUsageException("You can't marshal a null to referenced-DTO.");

            _null = true;

        } else {
            // Do the real marshal work.
            // logger.debug("marshal begin");

            // Always set stereotyped.
            if (!stereotyped)
                stereotyped = true;

            IMarshalSession session = getSession();
            Object marshalKey = getMarshalKey(source);
            D marshalled = session.getMarshalled(marshalKey);
            if (marshalled != null)
                /**
                 * 当 entity 被 marshal 多次，并且 marshal-type/fmask 不同时，则第一个进入 marshalled-cache 的 为有效。
                 * 这个背后的逻辑是：内部重复的通常只有一种可能，即 parent-ref。
                 */
                return (D) marshalled;

            session.addMarshalled(marshalKey, this);

            __marshal(source);
            _marshal(source);

            // logger.debug("marshal end");
        }

        return _this;
    }

    protected Object getMarshalKey(S source) {
        return new BaseDto_MKey(source, getClass(), marshalType);
    }

    protected Object getUnmarshalKey(S source) {
        return new BaseDto_MKey(source, getClass(), MarshalType.ANY);
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
    // public abstract <D extends BaseDto<S>> D ref(S source);

    @Override
    final S mergeImpl(S target) {

        // String prefix = Strings.repeat(sessionStack.size(), "    ");
        // System.err.println(prefix + "merge " + ObjectIndex.objid(this)
        // + ": " + ObjectIndex.objid(target));

        // Force stereotyped before unmarshal.
        if (!stereotyped)
            stereotyped = true;

        if (isNullRef())
            return null;

        @SuppressWarnings("unchecked")
        S deref = (S) mergeDeref(target);

        if (target == null || /* TODO Check this later */target == deref) {
            IMarshalSession session = getSession();
            S unmarshalled = session.getUnmarshalled(this);
            if (unmarshalled != null) {
                // System.err.println(prefix + "    cache-exist");
                return unmarshalled;
            }
            // System.err.println(prefix + "    cache-add");
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
     * @return Object instead of S to avoid compile problem. (This is just a work-around)
     */
    protected abstract Object mergeDeref(S given);

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

        marshalAs(MarshalType.SELECTION);

        __parse(map);
        _parse(map);
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
