package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.free.IllegalUsageException;
import javax.free.ParseException;
import javax.free.UnexpectedException;

import com.bee32.plover.arch.bean.IPropertyAccessor;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.arch.util.dto.BaseDto;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntitySpec;

/**
 * Entity1Dto* utilities.
 */
public abstract class DTOs {

    public static <E extends Entity<K>, D extends EntityDto<E, K>, K extends Serializable> //
    Class<? extends E> getEntityType(D dto) {
        return dto.getEntityType();
    }

    public static <D extends BaseDto<?>> D createDto(Class<D> dtoClass) {
        try {
            D dto = dtoClass.newInstance();
            return dto;
        } catch (ReflectiveOperationException e) {
            throw new IllegalUsageException("Can't create a DTO of " + dtoClass, e);
        }
    }

    public static <_S, _D extends BaseDto<? super _S>> _D _marshal(Class<_D> dtoClass, int fmask, _S source,
            Boolean refButFilled) {
        return new _NotUsed_Dto().marshal(dtoClass, fmask, source, refButFilled);
    }

    public static <_S, _D extends BaseDto<? super _S>> _D marshal(Class<_D> dtoClass, int fmask, _S source) {
        return new _NotUsed_Dto().marshal(dtoClass, fmask, source);
    }

    public static <_S, _D extends BaseDto<? super _S>> _D marshal(Class<_D> dtoClass, _S source) {
        return new _NotUsed_Dto().marshal(dtoClass, source);
    }

    public static <_S, _D extends BaseDto<? super _S>> _D marshal(Class<_D> dtoClass, _S source, Boolean refButFilled) {
        return new _NotUsed_Dto().marshal(dtoClass, source, refButFilled);
    }

    // @Deprecated
    public static <_S, _D extends BaseDto<? super _S>> _D mref(Class<_D> dtoClass, int fmask, _S source) {
        return new _NotUsed_Dto().mref(dtoClass, fmask, source);
    }

    public static <_S, _D extends BaseDto<? super _S>> _D mref(Class<_D> dtoClass, _S source) {
        return new _NotUsed_Dto().mref(dtoClass, source);
    }

    public static <_S, _D extends BaseDto<_S>> List<_D> _marshalList(Class<_D> dtoClass, int fmask,
            Iterable<? extends _S> sources, Boolean refButFilled) {
        return new _NotUsed_Dto()._marshalList(dtoClass, fmask, sources, refButFilled);
    }

    public static <_S, _D extends BaseDto<? super _S>> List<_D> marshalList(Class<_D> dtoClass, int fmask,
            Iterable<? extends _S> sources) {
        return new _NotUsed_Dto().marshalList(dtoClass, fmask, sources);
    }

    public static <_S, _D extends BaseDto<? super _S>> List<_D> marshalList(Class<_D> dtoClass,
            Iterable<? extends _S> sources) {
        return new _NotUsed_Dto().marshalList(dtoClass, sources);
    }

    public static <_S, _D extends BaseDto<? super _S>> List<_D> mrefList(Class<_D> dtoClass, int fmask,
            Iterable<? extends _S> sources) {
        return new _NotUsed_Dto().mrefList(dtoClass, fmask, sources);
    }

    public static <_S, _D extends BaseDto<? super _S>> List<_D> mrefList(Class<_D> dtoClass,
            Iterable<? extends _S> sources) {
        return new _NotUsed_Dto().mrefList(dtoClass, sources);
    }

    public static <_S, _D extends BaseDto<? super _S>> Set<_D> marshalSet(Class<_D> dtoClass, int fmask,
            Iterable<? extends _S> sources, Boolean refButFilled) {
        return new _NotUsed_Dto().marshalSet(dtoClass, fmask, sources, refButFilled);
    }

    public static <_S, _D extends BaseDto<? super _S>> Set<_D> marshalSet(Class<_D> dtoClass, int fmask,
            Iterable<? extends _S> sources) {
        return new _NotUsed_Dto().marshalSet(dtoClass, fmask, sources);
    }

    public static <_S, _D extends BaseDto<? super _S>> Set<_D> marshalSet(Class<_D> dtoClass,
            Iterable<? extends _S> sources) {
        return new _NotUsed_Dto().marshalSet(dtoClass, sources);
    }

    public static <_S, _D extends BaseDto<? super _S>> Set<_D> marshalSet(Class<_D> dtoClass, int fmask,
            Iterable<? extends _S> sources, boolean refButFilled) {
        return new _NotUsed_Dto().marshalSet(dtoClass, fmask, sources, refButFilled);
    }

    public static <_S, _D extends BaseDto<_S>> Set<_D> marshalSet(Class<_D> dtoClass, Iterable<? extends _S> sources,
            boolean refButFilled) {
        return new _NotUsed_Dto().marshalSet(dtoClass, sources, refButFilled);
    }

    public static <_S, __s> void merge(_S target, IPropertyAccessor<__s> property, BaseDto<__s> propertyDto) {
        new _NotUsed_Dto().merge(target, property, propertyDto);
    }

    public static <_S, __s> void merge(_S target, String propertyName, BaseDto<__s> propertyDto) {
        new _NotUsed_Dto().merge(target, propertyName, propertyDto);
    }

    public static <_E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> void mergeList(
            _E target, IPropertyAccessor<List<_e>> property, Iterable<? extends _d> dtoList) {
        new _NotUsed_Dto().mergeList(target, property, dtoList);
    }

    public static <_E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> void mergeList(
            _E target, String propertyName, Iterable<? extends _d> dtoList) {
        new _NotUsed_Dto().mergeList(target, propertyName, dtoList);
    }

    public static <_E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> void mergeSet(
            _E target, IPropertyAccessor<Set<_e>> property, Iterable<? extends _d> dtoList) {
        new _NotUsed_Dto().mergeSet(target, property, dtoList);
    }

    public static <_E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> void mergeSet(
            _E target, String propertyName, Iterable<? extends _d> dtoList) {
        new _NotUsed_Dto().mergeSet(target, propertyName, dtoList);
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

    public static <D extends EntityDto<?, ?>, K extends Serializable> //
    Map<K, D> index(Collection<? extends D> dtos) {
        Map<K, D> map = new LinkedHashMap<K, D>();
        for (D dto : dtos) {
            K id = (K) dto.getId();
            map.put(id, dto);
        }
        return map;
    }

    public static boolean isNull(EntityDto<?, ?> dto) {
        if (dto == null)
            return true;
        if (dto.isNull())
            return true;
        return false;
    }

}

class _NotUsed_
        extends EntitySpec<Serializable> {

    private static final long serialVersionUID = 1L;

    @Override
    public Serializable getId() {
        return null;
    }

    @Override
    protected void setId(Serializable id) {
    }

}

class _NotUsed_Dto
        extends EntityDto<_NotUsed_, Serializable> {

    private static final long serialVersionUID = 1L;

    public _NotUsed_Dto() {
        enter(getDefaultSession());
    }

    @Override
    protected void _marshal(_NotUsed_ source) {
        throw new UnexpectedException();
    }

    @Override
    protected void _unmarshalTo(_NotUsed_ target) {
        throw new UnexpectedException();
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new UnexpectedException();
    }

}
