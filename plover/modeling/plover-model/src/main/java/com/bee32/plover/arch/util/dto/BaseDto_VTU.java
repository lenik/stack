package com.bee32.plover.arch.util.dto;

import java.util.HashMap;
import java.util.Map;

import javax.free.ParseException;
import javax.free.TypeConvertException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.util.TextMap;

/**
 * Variant type utils.
 */
abstract class BaseDto_VTU<S, C>
        extends BaseDto_Skel<S, C> {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(BaseDto.class);

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

    void checkCanBeUnmarshalledTo() {
        if (getMarshalType().isReference())
            logger.warn("Unmarshal is ignored: unmarshalTo() is called but the DTO is a reference. "
                    + "You should use unmarshal() instead, to unmarshal non-filled DTOs.");
    }

    public final void unmarshalTo(IMarshalSession session, S target) {
        checkCanBeUnmarshalledTo();
        merge(session, target);
    }

    public final void unmarshalTo(C context, S target) {
        checkCanBeUnmarshalledTo();
        merge(context, target);
    }

    public final void unmarshalTo(S target) {
        checkCanBeUnmarshalledTo();
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
        parse(getDefaultContext(), map);
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
        parse(getDefaultContext(), request);
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

}
