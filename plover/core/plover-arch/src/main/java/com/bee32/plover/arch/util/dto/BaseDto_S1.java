package com.bee32.plover.arch.util.dto;

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
public abstract class BaseDto_S1<S, C>
        extends BaseDto_VTU<S, C> {

    private static final long serialVersionUID = 1L;

    public static <S, D extends BaseDto<S, C>, C> D marshal(IMarshalSession session, //
            Class<D> dtoClass, int selection, S source) {
        return marshal(session, dtoClass, selection, source, null);
    }

    /**
     * Marshal as selection or reference.
     *
     * @param refButFilled
     *            Non-<code>null</code> to marshal as a reference.
     */
    public static <S, D extends BaseDto<S, C>, C> D marshal(IMarshalSession session, //
            Class<D> dtoClass, int selection, S source, Boolean refButFilled) {
        D dto;
        try {
            dto = dtoClass.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to instantiate DTO " + dtoClass.getName(), e);
        }

        dto.setSelection(selection);

        if (refButFilled == null)
            dto.marshalAs(MarshalType.SELECTION);
        else
            dto.marshalAs(MarshalType.ID_REF);

        if (refButFilled == Boolean.FALSE) {
            // Simple reference only:
            dto.ref(source);

        } else {
            // Do the marshal.
            // the marshal() function will deal with null carefully.
            if (session == null)
                dto = dto.marshal(source);
            else
                dto = dto.marshal(session, source);
        }
        return dto;
    }

    public static <S, D extends BaseDto<S, C>, C> List<D> marshalList(IMarshalSession session, //
            Class<D> dtoClass, int selection, Iterable<? extends S> sources) {
        return marshalList(session, dtoClass, selection, sources, null);

    }

    public static <S, D extends BaseDto<S, C>, C> List<D> marshalList(IMarshalSession session, //
            Class<D> dtoClass, int selection, Iterable<? extends S> sources, Boolean refButFilled) {

        List<D> dtoList = new ArrayList<D>();

        if (sources == null)
            return dtoList;

        for (S _source : sources) {
            D dto = marshal(session, dtoClass, selection, _source, refButFilled);
            dtoList.add(dto);
        }

        return dtoList;
    }

    public static <S, D extends BaseDto<S, C>, C> Set<D> marshalSet(IMarshalSession session, //
            Class<D> dtoClass, int selection, Iterable<? extends S> sources) {
        return marshalSet(session, dtoClass, selection, sources, null);
    }

    public static <S, D extends BaseDto<S, C>, C> Set<D> marshalSet(IMarshalSession session, //
            Class<D> dtoClass, int selection, Iterable<? extends S> sources, Boolean refButFilled) {

        Set<D> dtoSet = new HashSet<D>();

        if (sources == null)
            return dtoSet;

        for (S _source : sources) {
            D dto = marshal(session, dtoClass, selection, _source, refButFilled);
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
