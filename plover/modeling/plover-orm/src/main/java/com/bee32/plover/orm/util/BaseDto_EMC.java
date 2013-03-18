package com.bee32.plover.orm.util;

import java.util.*;

import javax.free.NotImplementedException;

import com.bee32.plover.arch.bean.BeanPropertyAccessor;
import com.bee32.plover.arch.bean.IPropertyAccessor;
import com.bee32.plover.arch.util.dto.BaseDto;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.IMultiFormat;
import com.bee32.plover.util.PrettyPrintStream;

public abstract class BaseDto_EMC<S>
        extends BaseDto<S>
        implements IMultiFormat {

    private static final long serialVersionUID = 1L;

    protected static class ctx
            extends DefaultDataAssembledContext {
    }

    public BaseDto_EMC() {
        super();
    }

    public BaseDto_EMC(int fmask) {
        super(fmask);
    }

    /**
     * <pre>
     * LAYER 3: Unmarshal Collection
     * -----------------------------------------------------------------------
     *      unmarshalCollection
     *      unmarshalList
     *      unmarshalSet
     *      TODO unmarshalMap?
     * -----------------------------------------------------------------------
     * </pre>
     */

    /**
     * Experimental.
     */
    static boolean normalizeNulls = false;

    /**
     * It's not append, but assign to the collection.
     *
     * <pre>
     * null                     skip
     * *    not-filled  null    count(null)++
     * *    not-filled  id      if (! contains(id)) add(deref)
     * *    filled      null    add(unmarshal())
     * *    filled      id      if (contains(id)) unmarshalTo(existing)
     *                             else add(unmarshalTo(deref))
     * </pre>
     *
     * deref
     */
    <Coll extends Collection<_E>, _D extends EntityDto<_E, ?>, _E extends Entity<?>> //
    /*    */Coll mergeCollection(Coll collection, Iterable<? extends _D> dtoList) {

        if (collection == null)
            throw new NullPointerException("collection");

        if (dtoList == null)
            return collection;

        int nullCount = 0;

        List<_E> removeList = new ArrayList<_E>(collection);
        List<_E> addList = new ArrayList<_E>();

        Map<Object, _E> keyMap = new HashMap<Object, _E>();
        for (_E each : collection) {
            Object id = each.getId();
            if (id != null)
                keyMap.put(id, each);
        }

        Map<_E, _E> contentMap = new HashMap<_E, _E>();
        for (_E each : collection)
            if (each != null)
                contentMap.put(each, each);

        for (_D dto : dtoList) {
            if (dto == null) // DTO == null means ignore.
                continue;

            _E entity;

            if (dto.isNullRef()) {
                // entity = null;
                nullCount++;
                continue;
            }

            switch (dto.getMarshalType()) {
            case ID_REF:
            case ID_VER_REF: // TODO VER FIX.
                _E ref = keyMap.get(dto.id);
                if (ref != null) {

                    removeList.remove(ref);

                } else {
                    entity = dto.unmarshal(getSession());

                    addList.add(entity);
                }
                break;

            case NAME_REF:
            case OTHER_REF:
            default:
                throw new NotImplementedException();

            case SELECTION:
                if (dto.id == null) {
                    entity = dto.unmarshal(getSession());

                    _E reuse = contentMap.get(entity);
                    if (reuse != null) {
                        // We are not going to partial-modify elements in collection-unmarshalling.
                        // XXX dto.unmarshalTo(context, reuse);
                        // XXX reuse.populate(entity); ??
                        entity = reuse;
                        removeList.remove(reuse);
                    } else {
                        addList.add(entity);
                    }
                } else {
                    _E previous = keyMap.get(dto.id);
                    if (previous != null) {
                        dto.unmarshalTo(getSession(), previous);
                        removeList.remove(previous);

                    } else {
                        entity = dto.unmarshal(getSession());
                        addList.add(entity);
                    }
                }
            }
        } // for dtoList

        if (removeList != null)
            collection.removeAll(removeList);

        if (addList != null)
            collection.addAll(addList);

        if (normalizeNulls && nullCount != 0) {
            while (collection.contains(null))
                collection.remove(null);
            for (int i = 0; i < nullCount; i++)
                collection.add(null);
        }

        return collection;
    }

    // 以上。

    public <_D extends EntityDto<_E, ?>, _E extends Entity<?>> //
    /*    */List<_E> mergeList(List<_E> list, Iterable<? extends _D> dtoList) {
        if (list == null)
            list = new ArrayList<_E>();
        return mergeCollection(list, dtoList);
    }

    public <_D extends EntityDto<_E, ?>, _E extends Entity<?>> //
    /*    */Set<_E> mergeSet(Set<_E> set, Iterable<? extends _D> dtoList) {
        if (set == null)
            set = new HashSet<_E>();
        return mergeCollection(set, dtoList);
    }

    /**
     * <pre>
     * LAYER 4: Merge collection properties
     * -----------------------------------------------------------------------
     *      mergeCollection
     *      mergeList
     *      mergeSet
     *      TODO mergeMap?
     * -----------------------------------------------------------------------
     * </pre>
     */

    public <_E extends Entity<?>, _d extends EntityDto<_e, ?>, _e extends Entity<?>> //
    /*    */void mergeList(_E target, IPropertyAccessor<List<_e>> property, Iterable<? extends _d> dtoList) {

        List<_e> list = property.get(target);

        if (list == null)
            list = new ArrayList<_e>();

        list = mergeList(list, dtoList);

        property.set(target, list);
    }

    public <_E extends Entity<?>, _d extends EntityDto<_e, ?>, _e extends Entity<?>> //
    /*    */void mergeList(_E target, String propertyName, Iterable<? extends _d> dtoList) {

        Class<_E> targetType = (Class<_E>) target.getClass();

        IPropertyAccessor<List<_e>> property;
        property = BeanPropertyAccessor.access(targetType, propertyName);

        mergeList(target, property, dtoList);
    }

    public <_E extends Entity<?>, _d extends EntityDto<_e, ?>, _e extends Entity<?>> //
    /*    */void mergeSet(_E target, IPropertyAccessor<Set<_e>> property, Iterable<? extends _d> dtoList) {

        Set<_e> set = property.get(target);

        if (set == null)
            set = new HashSet<_e>();

        set = mergeSet(set, dtoList);

        property.set(target, set);
    }

    public <_E extends Entity<?>, _d extends EntityDto<_e, ?>, _e extends Entity<?>> //
    /*    */void mergeSet(_E target, String propertyName, Iterable<? extends _d> dtoList) {

        Class<_E> targetType = (Class<_E>) target.getClass();

        IPropertyAccessor<Set<_e>> property;
        property = BeanPropertyAccessor.access(targetType, propertyName);

        mergeSet(target, property, dtoList);
    }

    @Override
    public String toString() {
        return toString(FormatStyle.DEFAULT);
    }

    @Override
    public String toString(FormatStyle format) {
        PrettyPrintStream buf = new PrettyPrintStream();
        toString(buf, format);
        return buf.toString();
    }

    @Override
    public void toString(PrettyPrintStream out, FormatStyle format) {
        toString(out, format, null, 0);
    }

}
