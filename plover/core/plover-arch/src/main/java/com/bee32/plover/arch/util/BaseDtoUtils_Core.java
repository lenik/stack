package com.bee32.plover.arch.util;

import java.beans.IntrospectionException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.free.IllegalUsageException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataAccessException;

/**
 * <pre>
 * LAYER 3 - STATIC HELPER: MARSHAL
 * -----------------------------------------------------------------------
 *      This layer deals with generic marshal,
 *      for both scalar and collections.
 * </pre>
 */
public abstract class BaseDtoUtils_Core {

    /**
     * Generic marshal with nullable source support.
     */
    public static <S, D extends BaseDto<S, C>, C> D marshal(IMarshalSession session, //
            Class<D> dtoClass, Integer selection, S source) {
        D dto;
        try {
            if (selection == null)
                dto = dtoClass.newInstance();
            else {
                Constructor<D> ctor = dtoClass.getConstructor(int.class);
                dto = ctor.newInstance(selection.intValue());
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to instantiate DTO " + dtoClass.getName(), e);
        }

        // Do the marshal.
        // the marshal() function will deal with null carefully.
        if (session == null)
            dto = dto.marshal(source);
        else
            dto = dto.marshal(session, source);

        return dto;
    }

    public static <S, D extends BaseDto<S, C>, C> D marshal(IMarshalSession session, Class<D> dtoClass, S source) {
        return marshal(session, dtoClass, null, source);
    }

    public static <S, D extends BaseDto<S, C>, C> D marshal(Class<D> dtoClass, Integer selection, S source) {
        return marshal(null, dtoClass, selection, source);
    }

    public static <S, D extends BaseDto<S, C>, C> D marshal(Class<D> dtoClass, S source) {
        return marshal(null, dtoClass, null, source);
    }

    public static <S, D extends BaseDto<S, C>, C> List<D> marshalList(IMarshalSession session, Class<D> dtoClass,
            Integer selection, Iterable<? extends S> sources) {

        List<D> dtoList = new ArrayList<D>();

        if (sources == null)
            return dtoList;

        for (S _source : sources) {
            D dto = marshal(session, dtoClass, selection, _source);
            dtoList.add(dto);
        }

        return dtoList;
    }

    public static <S, D extends BaseDto<S, C>, C> List<D> marshalList(//
            IMarshalSession session, Class<D> dtoClass, Iterable<? extends S> sources) {
        return marshalList(session, dtoClass, null, sources);
    }

    public static <S, D extends BaseDto<S, C>, C> List<D> marshalList(//
            Class<D> dtoClass, Integer selection, Iterable<? extends S> sources) {
        return marshalList(null, dtoClass, selection, sources);
    }

    public static <S, D extends BaseDto<S, C>, C> List<D> marshalList(//
            Class<D> dtoClass, Iterable<? extends S> sources) {
        return marshalList(null, dtoClass, null, sources);
    }

    public static <S, D extends BaseDto<S, C>, C> Set<D> marshalSet(//
            IMarshalSession session, Class<D> dtoClass, Integer selection, Iterable<? extends S> sources) {

        Set<D> dtoSet = new HashSet<D>();

        if (sources == null)
            return dtoSet;

        for (S _source : sources) {
            D dto = marshal(session, dtoClass, selection, _source);
            dtoSet.add(dto);
        }

        return dtoSet;
    }

    public static <S, D extends BaseDto<S, C>, C> Set<D> marshalSet(//
            IMarshalSession session, Class<D> dtoClass, Iterable<? extends S> sources) {
        return marshalSet(session, dtoClass, null, sources);
    }

    public static <S, D extends BaseDto<S, C>, C> Set<D> marshalSet(//
            Class<D> dtoClass, Integer selection, Iterable<? extends S> sources) {
        return marshalSet(null, dtoClass, selection, sources);
    }

    public static <S, D extends BaseDto<S, C>, C> Set<D> marshalSet(//
            Class<D> dtoClass, Iterable<? extends S> sources) {
        return marshalSet(null, dtoClass, null, sources);
    }

    /**
     * <pre>
     * LAYER 3 - STATIC HELPER: UNMARSHAL
     * -----------------------------------------------------------------------
     *      Unmarshal collections.
     * </pre>
     */

    /**
     * In the base DTO implementation, no modification / ref is used.
     */
    public static <Coll extends Collection<S>, D extends BaseDto<S, C>, S, C> //
    /*    */Coll _unmarshalCollection(IMarshalSession session, Coll collection, Iterable<? extends D> dtoList) {

        if (collection == null)
            throw new NullPointerException("collection");

        collection.clear();

        if (dtoList != null)
            for (D dto : dtoList) {
                if (dto == null)
                    throw new NullPointerException("dto");

                S source = dto.merge(session, null);

                collection.add(source);
            }

        return collection;
    }

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

    /**
     * <pre>
     * LAYER 4 - PROPERTY MERGE
     * -----------------------------------------------------------------------
     *      merge(propertyAccessor)
     *      merge("property")
     * </pre>
     */
    public static <S, _s, C> void merge(IMarshalSession session, S target, //
            IPropertyAccessor<S, _s> property, BaseDto<_s, C> propertyDto) {

        // DTO == null means ignore.
        if (propertyDto == null)
            return;

        _s _old = property.get(target);
        _s _new;

        if (session == null)
            _new = propertyDto.merge(_old);
        else
            _new = propertyDto.merge(session, _old);

        if (_new != _old)
            property.set(target, _new);
    }

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
