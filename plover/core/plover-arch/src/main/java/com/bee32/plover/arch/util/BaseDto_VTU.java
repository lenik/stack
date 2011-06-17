package com.bee32.plover.arch.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.free.ParseException;
import javax.free.TypeConvertException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Variant type utils.
 */
abstract class BaseDto_VTU<S, C>
        extends BaseDto_ASM<S, C> {

    private static final long serialVersionUID = 1L;

    /**
     * <pre>
     * LAYER 1-X
     * -----------------------------------------------------------------------
     *      User friendly helpers.
     *
     *      These methods are provided for convienient programming.
     *      (And for compatibility purpose, too.)
     *
     * -----------------------------------------------------------------------
     * Note: The parse function:
     *
     *  parse(Request)
     *      parse(TextMap)
     *          parse(C, TextMap)
     *              parse(S, TextMap)
     *                  parseImpl(TextMap) <----.
     *                                          |
     *  parse(Map)                              |
     *      parse(C, Map)                       |
     *          parse(S, Map)                   |
     *              parseImpl(Map) -------------'
     *
     * </pre>
     */

    /**
     * (COMPAT) Unmarshal to a given target.
     */
    abstract boolean isUnmarshallable1();

    void checkCanBeUnmarshalled() {
        if (!isUnmarshallable1())
            logger.warn("Unmarshal is ignored: unmarshalTo() is called but the DTO is not filled. "
                    + "You should use unmarshal() instead, to unmarshal non-filled DTOs.");
    }

    public final void unmarshalTo(IMarshalSession session, S target) {
        checkCanBeUnmarshalled();
        merge(session, target);
    }

    public final void unmarshalTo(C context, S target) {
        checkCanBeUnmarshalled();
        merge(context, target);
    }

    public final void unmarshalTo(S target) {
        checkCanBeUnmarshalled();
        merge(target);
    }

    public final S unmarshal(IMarshalSession session) {
        return merge(session, null);
    }

    public final S unmarshal(C context) {
        return merge(context, null);
    }

    public final S unmarshal() {
        return merge(null);
    }

    public synchronized final void parse(IMarshalSession session, TextMap map)
            throws ParseException {
        enter(session);
        try {
            parseImpl(map);
        } finally {
            leave();
        }
    }

    public final void parse(C context, TextMap map)
            throws ParseException {
        parse(createOrReuseSession(context), map);
    }

    public final void parse(TextMap map)
            throws ParseException {
        parse(getDefaultMarshalContext(), map);
    }

    public final void parse(C context, HttpServletRequest request)
            throws ServletException {
        TextMap textMap = TextMap.convert(request);
        try {
            parse(context, textMap);
        } catch (TypeConvertException e) {
            throw new ServletException("Parse error: " + e.getMessage(), e);
        } catch (ParseException e) {
            throw new ServletException("Parse error: " + e.getMessage(), e);
        }
    }

    public final void parse(HttpServletRequest request)
            throws ServletException {
        parse(getDefaultMarshalContext(), request);
    }

    final void parseImpl(Map<String, ?> map)
            throws ParseException {
        if (map == null)
            throw new NullPointerException("map");

        TextMap textMap = TextMap.convert(map);

        try {
            parseImpl(textMap);
        } catch (TypeConvertException e) {
            throw new ParseException(e.getMessage(), e);
        }
    }

    public final Map<String, Object> exportMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        export(map);
        return map;
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
