package com.bee32.plover.orm.util;

import java.beans.IntrospectionException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.free.IllegalUsageException;
import javax.free.NotImplementedException;

import com.bee32.plover.arch.util.dto.BeanPropertyAccessor;
import com.bee32.plover.arch.util.dto.IPropertyAccessor;
import com.bee32.plover.orm.entity.Entity;

public abstract class EntityDto_SM1<E extends Entity<K>, K extends Serializable>
        extends EntityDto_VTU<E, K> {

    private static final long serialVersionUID = 1L;

    public EntityDto_SM1() {
        super();
    }

    public EntityDto_SM1(int selection) {
        super(selection);
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
    <Coll extends Collection<_E>, D extends EntityDto<_E, _K>, _E extends Entity<_K>, _K extends Serializable> //
    /*    */Coll mergeCollection(Coll collection, Iterable<? extends D> dtoList) {

        if (collection == null)
            throw new NullPointerException("collection");

        if (dtoList == null)
            return collection;

        int nullCount = 0;

        List<_E> removeList = new ArrayList<_E>(collection);
        List<_E> addList = new ArrayList<_E>();

        Map<_K, _E> keyMap = new HashMap<_K, _E>();
        for (_E each : collection) {
            _K id = each.getId();
            if (id != null)
                keyMap.put(id, each);
        }

        Map<_E, _E> contentMap = new HashMap<_E, _E>();
        for (_E each : collection)
            if (each != null)
                contentMap.put(each, each);

        for (D dto : dtoList) {
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

    public <_D extends EntityDto<_E, _K>, _E extends Entity<_K>, _K extends Serializable> //
    /*    */List<_E> mergeList(List<_E> list, Iterable<? extends _D> dtoList) {
        if (list == null)
            list = new ArrayList<_E>();
        return mergeCollection(list, dtoList);
    }

    public <_D extends EntityDto<_E, _K>, _E extends Entity<_K>, _K extends Serializable> //
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

    public <_E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> //
    /*    */void mergeList(_E target, IPropertyAccessor<_E, List<_e>> property, Iterable<? extends _d> dtoList) {

        List<_e> list = property.get(target);

        if (list == null)
            list = new ArrayList<_e>();

        list = mergeList(list, dtoList);

        property.set(target, list);
    }

    public <_E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> //
    /*    */void mergeList(_E target, String propertyName, Iterable<? extends _d> dtoList) {

        Class<_E> targetType = (Class<_E>) target.getClass();

        IPropertyAccessor<_E, List<_e>> property;
        try {
            property = BeanPropertyAccessor.access(targetType, propertyName);
        } catch (IntrospectionException e) {
            // XXX Error message?
            throw new IllegalUsageException(e.getMessage(), e);
        }

        mergeList(target, property, dtoList);
    }

    public <_E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> //
    /*    */void mergeSet(_E target, IPropertyAccessor<_E, Set<_e>> property, Iterable<? extends _d> dtoList) {

        Set<_e> set = property.get(target);

        if (set == null)
            set = new HashSet<_e>();

        set = mergeSet(set, dtoList);

        property.set(target, set);
    }

    public <_E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> //
    /*    */void mergeSet(_E target, String propertyName, Iterable<? extends _d> dtoList) {

        Class<_E> targetType = (Class<_E>) target.getClass();

        IPropertyAccessor<_E, Set<_e>> property;
        try {
            property = BeanPropertyAccessor.access(targetType, propertyName);
        } catch (IntrospectionException e) {
            // XXX Error message?
            throw new IllegalUsageException(e.getMessage(), e);
        }

        mergeSet(target, property, dtoList);
    }

}
