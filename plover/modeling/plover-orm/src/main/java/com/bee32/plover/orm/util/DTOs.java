package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.free.ParseException;
import javax.free.UnexpectedException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.arch.util.dto.BaseDto;
import com.bee32.plover.arch.util.dto.IPropertyAccessor;
import com.bee32.plover.orm.entity.Entity;

/**
 * Entity1Dto* utilities.
 */
public abstract class DTOs {

    public static <E extends Entity<K>, D extends EntityDto<E, K>, K extends Serializable> //
    Class<? extends E> getEntityType(D dto) {
        return dto.getEntityType();
    }

    static class Dummy
            extends EntityDto<Entity<Serializable>, Serializable> {

        private static final long serialVersionUID = 1L;

        public Dummy() {
            enter(getDefaultSession());
        }

        @Override
        protected void _marshal(Entity<Serializable> source) {
            throw new UnexpectedException();
        }

        @Override
        protected void _unmarshalTo(Entity<Serializable> target) {
            throw new UnexpectedException();
        }

        @Override
        protected void _parse(TextMap map)
                throws ParseException {
            throw new UnexpectedException();
        }

    }

    public static <_S, _D extends BaseDto<? super _S, _C>, _C> _D _marshal(Class<_D> dtoClass, int selection, _S source,
            Boolean refButFilled) {
        return new Dummy().marshal(dtoClass, selection, source, refButFilled);
    }

    public static <_S, _D extends BaseDto<? super _S, _C>, _C> _D marshal(Class<_D> dtoClass, int selection, _S source) {
        return new Dummy().marshal(dtoClass, selection, source);
    }

    public static <_S, _D extends BaseDto<? super _S, _C>, _C> _D marshal(Class<_D> dtoClass, _S source) {
        return new Dummy().marshal(dtoClass, source);
    }

    public static <_S, _D extends BaseDto<? super _S, _C>, _C> _D marshal(Class<_D> dtoClass, _S source, Boolean refButFilled) {
        return new Dummy().marshal(dtoClass, source, refButFilled);
    }

    // @Deprecated
    public static <_S, _D extends BaseDto<? super _S, _C>, _C> _D mref(Class<_D> dtoClass, int selection, _S source) {
        return new Dummy().mref(dtoClass, selection, source);
    }

    public static <_S, _D extends BaseDto<? super _S, _C>, _C> _D mref(Class<_D> dtoClass, _S source) {
        return new Dummy().mref(dtoClass, source);
    }

    public static <_S, _D extends BaseDto<_S, _C>, _C> List<_D> _marshalList(Class<_D> dtoClass, int selection,
            Iterable<? extends _S> sources, Boolean refButFilled) {
        return new Dummy()._marshalList(dtoClass, selection, sources, refButFilled);
    }

    public static <_S, _D extends BaseDto<? super _S, _C>, _C> List<_D> marshalList(Class<_D> dtoClass, int selection,
            Iterable<? extends _S> sources) {
        return new Dummy().marshalList(dtoClass, selection, sources);
    }

    public static <_S, _D extends BaseDto<? super _S, _C>, _C> List<_D> marshalList(Class<_D> dtoClass,
            Iterable<? extends _S> sources) {
        return new Dummy().marshalList(dtoClass, sources);
    }

    public static <_S, _D extends BaseDto<? super _S, _C>, _C> List<_D> mrefList(Class<_D> dtoClass, int selection,
            Iterable<? extends _S> sources) {
        return new Dummy().mrefList(dtoClass, selection, sources);
    }

    public static <_S, _D extends BaseDto<? super _S, _C>, _C> List<_D> mrefList(Class<_D> dtoClass,
            Iterable<? extends _S> sources) {
        return new Dummy().mrefList(dtoClass, sources);
    }

    public static <_S, _D extends BaseDto<? super _S, _C>, _C> Set<_D> marshalSet(Class<_D> dtoClass, int selection,
            Iterable<? extends _S> sources, Boolean refButFilled) {
        return new Dummy().marshalSet(dtoClass, selection, sources, refButFilled);
    }

    public static <_S, _D extends BaseDto<? super _S, _C>, _C> Set<_D> marshalSet(Class<_D> dtoClass, int selection,
            Iterable<? extends _S> sources) {
        return new Dummy().marshalSet(dtoClass, selection, sources);
    }

    public static <_S, _D extends BaseDto<? super _S, _C>, _C> Set<_D> marshalSet(Class<_D> dtoClass,
            Iterable<? extends _S> sources) {
        return new Dummy().marshalSet(dtoClass, sources);
    }

    public static <_S, _D extends BaseDto<? super _S, _C>, _C> Set<_D> marshalSet(Class<_D> dtoClass, int selection,
            Iterable<? extends _S> sources, boolean refButFilled) {
        return new Dummy().marshalSet(dtoClass, selection, sources, refButFilled);
    }

    public static <_S, _D extends BaseDto<_S, _C>, _C> Set<_D> marshalSet(Class<_D> dtoClass,
            Iterable<? extends _S> sources, boolean refButFilled) {
        return new Dummy().marshalSet(dtoClass, sources, refButFilled);
    }

    public static <_S, __s, _C> void merge(_S target, IPropertyAccessor<_S, __s> property, BaseDto<__s, _C> propertyDto) {
        new Dummy().merge(target, property, propertyDto);
    }

    public static <_S, __s, _C> void merge(_S target, String propertyName, BaseDto<__s, _C> propertyDto) {
        new Dummy().merge(target, propertyName, propertyDto);
    }

    public static <_E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> void mergeList(
            _E target, IPropertyAccessor<_E, List<_e>> property, Iterable<? extends _d> dtoList) {
        new Dummy().mergeList(target, property, dtoList);
    }

    public static <_E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> void mergeList(
            _E target, String propertyName, Iterable<? extends _d> dtoList) {
        new Dummy().mergeList(target, propertyName, dtoList);
    }

    public static <_E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> void mergeSet(
            _E target, IPropertyAccessor<_E, Set<_e>> property, Iterable<? extends _d> dtoList) {
        new Dummy().mergeSet(target, property, dtoList);
    }

    public static <_E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> void mergeSet(
            _E target, String propertyName, Iterable<? extends _d> dtoList) {
        new Dummy().mergeSet(target, propertyName, dtoList);
    }

    public static boolean equals(EntityDto<?, ?> a, EntityDto<?, ?> b) {
        if (a == b)
            return true;
        if (a == null || b == null)
            return false;

        if (a.isNull() && b.isNull())
            return true;

        return a.equals(b);
    }

}
