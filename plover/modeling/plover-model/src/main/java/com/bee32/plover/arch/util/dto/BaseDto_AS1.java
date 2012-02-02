package com.bee32.plover.arch.util.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.bee32.plover.arch.bean.IPropertyAccessor;

/**
 * <pre>
 * LAYER 3 - STATIC HELPER: MARSHAL
 * -----------------------------------------------------------------------
 *      This layer deals with generic marshal,
 *      for both scalar and collections.
 * </pre>
 */
public abstract class BaseDto_AS1<S, C>
        extends BaseDto_VTU<S, C> {

    private static final long serialVersionUID = 1L;

    /**
     * Marshal as selection or reference.
     *
     * @param refButFilled
     *            Non-<code>null</code> to marshal as a reference.
     */
    public <_S, _D extends BaseDto<? super _S, _C>, _C> _D marshal(//
            Class<_D> dtoClass, int selection, _S source, Boolean refButFilled) {
        _D dto;
        try {
            dto = dtoClass.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to instantiate DTO " + dtoClass.getName(), e);
        }

        dto.setSelection(selection);

        boolean isRef = refButFilled != null;
        boolean doMarshal = refButFilled != Boolean.FALSE;

        if (isRef)
            dto.marshalAs(MarshalType.ID_REF);
        else
            dto.marshalAs(MarshalType.SELECTION);

        if (doMarshal) {
            // Do the marshal.
            // null source should make this dto._null = true
            dto = (_D) dto.marshal(getSession(), source);
        }

        if (isRef) {
            // Change to a reference.
            // null source should make this dto ref (id=null)
            dto.ref(source); // this will also set marshal-as ID_REF.
        }

        return dto;
    }

    public <_S, _D extends BaseDto<? super _S, _C>, _C> List<_D> _marshalList(//
            Class<_D> dtoClass, int selection, Iterable<? extends _S> sources, Boolean refButFilled) {

        List<_D> dtoList = new ArrayList<_D>();

        if (sources == null)
            return dtoList;

        for (_S _source : sources) {
            _D dto = marshal(dtoClass, selection, _source, refButFilled);
            dtoList.add(dto);
        }

        return dtoList;
    }

    public <_S, _D extends BaseDto<? super _S, _C>, _C> Set<_D> marshalSet( //
            Class<_D> dtoClass, int selection, Iterable<? extends _S> sources, Boolean refButFilled) {

        Set<_D> dtoSet = new HashSet<_D>();

        if (sources == null)
            return dtoSet;

        for (_S _source : sources) {
            _D dto = marshal(dtoClass, selection, _source, refButFilled);
            dtoSet.add(dto);
        }

        return dtoSet;
    }

    public <_S, _D extends BaseDto<? super _S, _C>, _C> Set<_D> marshalTreeSet( //
            Class<_D> dtoClass, int selection, Iterable<? extends _S> sources, Boolean refButFilled) {

        Set<_D> dtoSet = new TreeSet<_D>();

        if (sources == null)
            return dtoSet;

        for (_S _source : sources) {
            _D dto = marshal(dtoClass, selection, _source, refButFilled);
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
    public <Coll extends Collection<_S>, _D extends BaseDto<_S, _C>, _S, _C> //
    /*    */Coll _unmarshalCollection(Coll collection, Iterable<? extends _D> dtoList) {

        if (collection == null)
            throw new NullPointerException("collection");

        collection.clear();

        if (dtoList != null)
            for (_D dto : dtoList) {
                if (dto == null)
                    throw new NullPointerException("dto");

                _S source = dto.merge(getSession(), null);

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
    public <target_t, property_t, _C> void merge(//
            target_t target, IPropertyAccessor<property_t> property, BaseDto<property_t, _C> propertyDto) {

        if (property == null)
            throw new NullPointerException("property");

        // DTO == null means ignore.
        if (propertyDto == null)
            return;

        property_t _old = property.get(target);
        property_t _new;

        _new = propertyDto.merge(getSession(), _old);

        if (_new != _old)
            property.set(target, _new);
    }

}
