package com.bee32.plover.arch.util.dto;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

}
