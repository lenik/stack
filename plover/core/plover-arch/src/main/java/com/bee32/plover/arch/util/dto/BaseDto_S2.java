package com.bee32.plover.arch.util.dto;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.free.IllegalUsageException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataAccessException;

public abstract class BaseDto_S2<S, C>
        extends BaseDto_S1<S, C> {

    private static final long serialVersionUID = 1L;

    // marshal*

    public static <S, D extends BaseDto<S, C>, C> D marshal(IMarshalSession session, //
            Class<D> dtoClass, S source) {
        return marshal(session, dtoClass, -1, source);
    }

    public static <S, D extends BaseDto<S, C>, C> D marshal(Class<D> dtoClass, int selection, S source) {
        return marshal(null, dtoClass, selection, source);
    }

    public static <S, D extends BaseDto<S, C>, C> D marshal(Class<D> dtoClass, S source) {
        return marshal(null, dtoClass, -1, source);
    }

    public static <S, D extends BaseDto<S, C>, C> D marshal(IMarshalSession session, //
            Class<D> dtoClass, S source, boolean refButFilled) {
        return marshal(session, dtoClass, -1, source, refButFilled);
    }

    public static <S, D extends BaseDto<S, C>, C> D marshal(Class<D> dtoClass, int selection, S source,
            boolean refButFilled) {
        return marshal(null, dtoClass, selection, source, refButFilled);
    }

    public static <S, D extends BaseDto<S, C>, C> D marshal(Class<D> dtoClass, S source, boolean refButFilled) {
        return marshal(null, dtoClass, -1, source, refButFilled);
    }

    // mref(...) = marshal(..., true)
    public static <S, D extends BaseDto<S, C>, C> D mref(IMarshalSession session, //
            Class<D> dtoClass, S source) {
        return marshal(session, dtoClass, -1, source, true);
    }

    public static <S, D extends BaseDto<S, C>, C> D mref(Class<D> dtoClass, int selection, S source) {
        return marshal(null, dtoClass, selection, source, true);
    }

    public static <S, D extends BaseDto<S, C>, C> D mref(Class<D> dtoClass, S source) {
        return marshal(null, dtoClass, -1, source, true);
    }

    // marshalList*

    public static <S, D extends BaseDto<S, C>, C> List<D> marshalList(//
            IMarshalSession session, Class<D> dtoClass, Iterable<? extends S> sources) {
        return marshalList(session, dtoClass, -1, sources);
    }

    public static <S, D extends BaseDto<S, C>, C> List<D> marshalList(//
            Class<D> dtoClass, int selection, Iterable<? extends S> sources) {
        return marshalList(null, dtoClass, selection, sources);
    }

    public static <S, D extends BaseDto<S, C>, C> List<D> marshalList(//
            Class<D> dtoClass, Iterable<? extends S> sources) {
        return marshalList(null, dtoClass, -1, sources);
    }

    public static <S, D extends BaseDto<S, C>, C> List<D> marshalList(//
            IMarshalSession session, Class<D> dtoClass, Iterable<? extends S> sources, boolean refButFilled) {
        return marshalList(session, dtoClass, -1, sources, refButFilled);
    }

    public static <S, D extends BaseDto<S, C>, C> List<D> marshalList(//
            Class<D> dtoClass, int selection, Iterable<? extends S> sources, boolean refButFilled) {
        return marshalList(null, dtoClass, selection, sources, refButFilled);
    }

    public static <S, D extends BaseDto<S, C>, C> List<D> marshalList(//
            Class<D> dtoClass, Iterable<? extends S> sources, boolean refButFilled) {
        return marshalList(null, dtoClass, -1, sources, refButFilled);
    }

    // marshalSet

    public static <S, D extends BaseDto<S, C>, C> Set<D> marshalSet(//
            IMarshalSession session, Class<D> dtoClass, Iterable<? extends S> sources) {
        return marshalSet(session, dtoClass, -1, sources);
    }

    public static <S, D extends BaseDto<S, C>, C> Set<D> marshalSet(//
            Class<D> dtoClass, int selection, Iterable<? extends S> sources) {
        return marshalSet(null, dtoClass, selection, sources);
    }

    public static <S, D extends BaseDto<S, C>, C> Set<D> marshalSet(//
            Class<D> dtoClass, Iterable<? extends S> sources) {
        return marshalSet(null, dtoClass, -1, sources);
    }

    public static <S, D extends BaseDto<S, C>, C> Set<D> marshalSet(//
            IMarshalSession session, Class<D> dtoClass, Iterable<? extends S> sources, boolean refButFilled) {
        return marshalSet(session, dtoClass, -1, sources, refButFilled);
    }

    public static <S, D extends BaseDto<S, C>, C> Set<D> marshalSet(//
            Class<D> dtoClass, int selection, Iterable<? extends S> sources, boolean refButFilled) {
        return marshalSet(null, dtoClass, selection, sources, refButFilled);
    }

    public static <S, D extends BaseDto<S, C>, C> Set<D> marshalSet(//
            Class<D> dtoClass, Iterable<? extends S> sources, boolean refButFilled) {
        return marshalSet(null, dtoClass, -1, sources, refButFilled);
    }

    // _unmarshalCollection*

    public static <S, D extends BaseDto<S, C>, C> //
    /*    */List<S> _unmarshalList(IMarshalSession session, Iterable<? extends D> dtoList) {
        return _unmarshalCollection(session, new ArrayList<S>(), dtoList);
    }

    public static <S, D extends BaseDto<S, C>, C> //
    /*    */Set<S> _unmarshalSet(IMarshalSession session, Iterable<? extends D> dtoList) {
        return _unmarshalCollection(session, new HashSet<S>(), dtoList);
    }

    public static <Coll extends Collection<S>, D extends BaseDto<S, C>, S, C> //
    /*    */Coll _unmershalCollection(Coll collection, Iterable<? extends D> dtoList) {
        return _unmarshalCollection(null, collection, dtoList);
    }

    public static <S, D extends BaseDto<S, C>, C> //
    /*    */List<S> _unmershalList(Iterable<? extends D> dtoList) {
        return _unmarshalCollection(null, new ArrayList<S>(), dtoList);
    }

    public static <S, D extends BaseDto<S, C>, C> //
    /*    */Set<S> _unmarshalSet(Iterable<? extends D> dtoList) {
        return _unmarshalCollection(null, new HashSet<S>(), dtoList);
    }

    // merge*

    public static <S, _s, C> void merge(S target, //
            IPropertyAccessor<S, _s> property, BaseDto<_s, C> propertyDto) {
        merge(null, target, property, propertyDto);
    }

    public static <S, _s, C> void merge(IMarshalSession session, S target, //
            String propertyName, BaseDto<_s, C> propertyDto) {

        // DTO == null means ignore.
        if (propertyDto == null)
            return;

        Class<S> targetType = (Class<S>) target.getClass();

        IPropertyAccessor<S, _s> property;
        try {
            property = BeanPropertyAccessor.access(targetType, propertyName);
        } catch (IntrospectionException e) {
            // XXX Error message?
            throw new IllegalUsageException(e.getMessage(), e);
        }

        merge(session, target, property, propertyDto);
    }

    /**
     * Unmarshal a property DTO into the corresponding property of an entity in a smart way.
     * <p>
     * The contents in the given property DTO may be fully unmarshalled to a new created entity
     * bean, or unmarshalled to be reference to the existing entity, or between. The behaviour is
     * determined on the two states of the DTO object: the <i>filled</i> state, and the <i>id</i>
     * state.
     * <p>
     *
     * Transfer table:
     * <table border="1">
     * <tr>
     * <th>DTO</th>
     * <th>DTO.filled</th>
     * <th>DTO.id</th>
     * <th>Meaning</th>
     * <th>Result</th>
     * </tr>
     * <tr>
     * <td align="center"><code>null</code></td>
     * <td align="center">(n/a)</td>
     * <td align="center">(n/a)</td>
     * <td align="center">Skip, no change</td>
     * <td align="left">ENTITY</td>
     * </tr>
     * <tr>
     * <td align="center">*</td>
     * <td align="center">not-filled</td>
     * <td align="center"><code>null</code></td>
     * <td align="center">Set to null</td>
     * <td align="left"><code>null</code></td>
     * </tr>
     * <tr>
     * <td align="center">*</td>
     * <td align="center">not-filled</td>
     * <td align="center">id</td>
     * <td align="center">Reference</td>
     * <td align="left">ENTITY = reference&lt;dto.id&gt;</td>
     * </tr>
     * <tr>
     * <td align="center">*</td>
     * <td align="center">filled</td>
     * <td align="center"><code>null</code></td>
     * <td align="center">Full Reconstruction</td>
     * <td align="left">ENTITY = unmarshal()</td>
     * </tr>
     * <tr>
     * <td align="center">*</td>
     * <td align="center">filled</td>
     * <td align="center">id</td>
     * <td align="center">Partial Modification</td>
     * <td align="left">unmarshalTo(ENTITY = reference&lt;dto.id&gt;)</td>
     * </tr>
     * </table>
     *
     * The DTO is only filled if:
     * <ul>
     * <li>It has been {@link BaseDto#marshal(Object) marhsalled} from an entity bean.
     * <li>It has {@link BaseDto#parse(Map) parsed from a Map}, or
     * {@link BaseDto#parse(HttpServletRequest) from an HttpServletRequest}.</li>
     *
     * You can always clear the filled state by reference the DTO to a specific id, by calling the
     * {@link EntityDto#ref(Entity) ref} method.
     *
     * @param property
     *            The property accessor which is used to get the old property value, and set the
     *            property to a new value.
     * @param dto
     *            The data transfer object which will be unmarshalled into the property of the
     *            context target bean, using the specified property accessor.
     * @return This {@link WithContext} object, for chaining method calls purpose.
     * @throws DataAccessException
     *             If data access exception happened with calls into the
     *             {@link IEntityMarshalContext}.
     */
    public static <S, _s, C> void merge(S target, //
            String propertyName, BaseDto<_s, C> propertyDto) {
        // IMarshalSession session = getSession();
        merge(null, target, propertyName, propertyDto);
    }

}
