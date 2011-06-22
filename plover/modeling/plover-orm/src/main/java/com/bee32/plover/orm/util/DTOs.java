package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.arch.util.dto.BaseDto;
import com.bee32.plover.arch.util.dto.IPropertyAccessor;
import com.bee32.plover.orm.entity.Entity;

/**
 * EntityDto* utilities.
 */
public abstract class DTOs {

    private static final long serialVersionUID = 1L;

    static final EntityDto<Entity<Serializable>, Serializable> instance;

    static {
        instance = new EntityDto<Entity<Serializable>, Serializable>() {

            private static final long serialVersionUID = 1L;

            @Override
            protected void _unmarshalTo(Entity<Serializable> target) {
            }

            @Override
            protected void _parse(TextMap map)
                    throws ParseException {
            }

            @Override
            protected void _marshal(Entity<Serializable> source) {
            }

        };
    }

    public static <_S, _D extends BaseDto<_S, _C>, _C> _D marshal(Class<_D> dtoClass, int selection, _S source) {
        return instance.marshal(dtoClass, selection, source);
    }

    public static <_S, _D extends BaseDto<_S, _C>, _C> _D marshal(Class<_D> dtoClass, int selection, _S source,
            Boolean refButFilled) {
        return instance.marshal(dtoClass, selection, source, refButFilled);
    }

    public static <_S, _D extends BaseDto<_S, _C>, _C> _D marshal(Class<_D> dtoClass, _S source) {
        return instance.marshal(dtoClass, source);
    }

    public static <_S, _D extends BaseDto<_S, _C>, _C> _D marshal(Class<_D> dtoClass, int selection, _S source,
            boolean refButFilled) {
        return instance.marshal(dtoClass, selection, source, refButFilled);
    }

    public static <_S, _D extends BaseDto<_S, _C>, _C> _D marshal(Class<_D> dtoClass, _S source, boolean refButFilled) {
        return instance.marshal(dtoClass, source, refButFilled);
    }

    public static <_S, _D extends BaseDto<_S, _C>, _C> _D mref(Class<_D> dtoClass, int selection, _S source) {
        return instance.mref(dtoClass, selection, source);
    }

    public static <_S, _D extends BaseDto<_S, _C>, _C> _D mref(Class<_D> dtoClass, _S source) {
        return instance.mref(dtoClass, source);
    }

    public static <_S, _D extends BaseDto<_S, _C>, _C> List<_D> marshalList(Class<_D> dtoClass, int selection,
            Iterable<? extends _S> sources, Boolean refButFilled) {
        return instance.marshalList(dtoClass, selection, sources, refButFilled);
    }

    public static <_S, _D extends BaseDto<_S, _C>, _C> List<_D> marshalList(Class<_D> dtoClass, int selection,
            Iterable<? extends _S> sources) {
        return instance.marshalList(dtoClass, selection, sources);
    }

    public static <_S, _D extends BaseDto<_S, _C>, _C> List<_D> marshalList(Class<_D> dtoClass,
            Iterable<? extends _S> sources) {
        return instance.marshalList(dtoClass, sources);
    }

    public static <_S, _D extends BaseDto<_S, _C>, _C> List<_D> marshalList(Class<_D> dtoClass, int selection,
            Iterable<? extends _S> sources, boolean refButFilled) {
        return instance.marshalList(dtoClass, selection, sources, refButFilled);
    }

    public static <_S, _D extends BaseDto<_S, _C>, _C> Set<_D> marshalSet(Class<_D> dtoClass, int selection,
            Iterable<? extends _S> sources, Boolean refButFilled) {
        return instance.marshalSet(dtoClass, selection, sources, refButFilled);
    }

    public static <_S, _D extends BaseDto<_S, _C>, _C> List<_D> marshalList(Class<_D> dtoClass,
            Iterable<? extends _S> sources, boolean refButFilled) {
        return instance.marshalList(dtoClass, sources, refButFilled);
    }

    public static <_S, _D extends BaseDto<_S, _C>, _C> Set<_D> marshalSet(Class<_D> dtoClass, int selection,
            Iterable<? extends _S> sources) {
        return instance.marshalSet(dtoClass, selection, sources);
    }

    public static <_S, _D extends BaseDto<_S, _C>, _C> Set<_D> marshalSet(Class<_D> dtoClass,
            Iterable<? extends _S> sources) {
        return instance.marshalSet(dtoClass, sources);
    }

    public static <_S, _D extends BaseDto<_S, _C>, _C> Set<_D> marshalSet(Class<_D> dtoClass, int selection,
            Iterable<? extends _S> sources, boolean refButFilled) {
        return instance.marshalSet(dtoClass, selection, sources, refButFilled);
    }

    public static <_S, _D extends BaseDto<_S, _C>, _C> Set<_D> marshalSet(Class<_D> dtoClass,
            Iterable<? extends _S> sources, boolean refButFilled) {
        return instance.marshalSet(dtoClass, sources, refButFilled);
    }

    public static <_S, __s, _C> void merge(_S target, IPropertyAccessor<_S, __s> property, BaseDto<__s, _C> propertyDto) {
        instance.merge(target, property, propertyDto);
    }

    public static <_S, __s, _C> void merge(_S target, String propertyName, BaseDto<__s, _C> propertyDto) {
        instance.merge(target, propertyName, propertyDto);
    }

    public static <_E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> void mergeList(
            _E target, IPropertyAccessor<_E, List<_e>> property, Iterable<? extends _d> dtoList) {
        instance.mergeList(target, property, dtoList);
    }

    public static <_E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> void mergeList(
            _E target, String propertyName, Iterable<? extends _d> dtoList) {
        instance.mergeList(target, propertyName, dtoList);
    }

    public static <_E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> void mergeSet(
            _E target, IPropertyAccessor<_E, Set<_e>> property, Iterable<? extends _d> dtoList) {
        instance.mergeSet(target, property, dtoList);
    }

    public static <_E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> void mergeSet(
            _E target, String propertyName, Iterable<? extends _d> dtoList) {
        instance.mergeSet(target, propertyName, dtoList);
    }

}
