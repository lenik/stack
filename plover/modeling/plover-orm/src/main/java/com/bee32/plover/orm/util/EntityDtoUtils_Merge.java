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

import com.bee32.plover.arch.util.dto.BaseDtoUtils;
import com.bee32.plover.arch.util.dto.BeanPropertyAccessor;
import com.bee32.plover.arch.util.dto.IMarshalSession;
import com.bee32.plover.arch.util.dto.IPropertyAccessor;
import com.bee32.plover.orm.entity.Entity;

public class EntityDtoUtils_Merge
        extends BaseDtoUtils {

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
    static <Coll extends Collection<E>, D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    /*    */Coll mergeCollection(IMarshalSession session, Coll collection, Iterable<? extends D> dtoList) {

        if (collection == null)
            throw new NullPointerException("collection");

        if (dtoList == null)
            return collection;

        int nullCount = 0;

        List<E> removeList = new ArrayList<E>(collection);
        List<E> addList = new ArrayList<E>();

        Map<K, E> keyMap = new HashMap<K, E>();
        for (E each : collection) {
            K id = each.getId();
            if (id != null)
                keyMap.put(id, each);
        }

        Map<E, E> contentMap = new HashMap<E, E>();
        for (E each : collection)
            if (each != null)
                contentMap.put(each, each);

        for (D dto : dtoList) {
            if (dto == null) // DTO == null means ignore.
                continue;

            E entity;

            if (dto.isNullRef()) {
                // entity = null;
                nullCount++;
                continue;
            }

            switch (dto.getMarshalType()) {
            case ID_REF:
            case ID_VER_REF: // TODO VER FIX.
                E ref = keyMap.get(dto.id);
                if (ref != null) {

                    removeList.remove(ref);

                } else {
                    if (session == null)
                        entity = dto.unmarshal();
                    else
                        entity = dto.unmarshal(session);

                    addList.add(entity);
                }
                break;

            case NAME_REF:
            case OTHER_REF:
            default:
                throw new NotImplementedException();

            case SELECTION:
                if (dto.id == null) {
                    if (session == null)
                        entity = dto.unmarshal();
                    else
                        entity = dto.unmarshal(session);

                    E reuse = contentMap.get(entity);
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
                    E previous = keyMap.get(dto.id);
                    if (previous != null) {
                        if (session == null)
                            dto.unmarshalTo(previous);
                        else
                            dto.unmarshalTo(session, previous);

                        removeList.remove(previous);

                    } else {
                        if (session == null)
                            entity = dto.unmarshal();
                        else
                            entity = dto.unmarshal(session);

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

    static <Coll extends Collection<E>, D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    /*    */Coll mergeCollection(Coll collection, Iterable<? extends D> dtoList) {
        return mergeCollection((IMarshalSession) null, collection, dtoList);
    }

    public static <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    /*    */List<E> mergeList(IMarshalSession session, List<E> list, Iterable<? extends D> dtoList) {
        if (list == null)
            list = new ArrayList<E>();
        return mergeCollection(session, list, dtoList);
    }

    public static <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    /*    */List<E> mergeList(List<E> list, Iterable<? extends D> dtoList) {
        if (list == null)
            list = new ArrayList<E>();
        return mergeCollection((IMarshalSession) null, list, dtoList);
    }

    public static <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    /*    */Set<E> mergeSet(IMarshalSession session, Set<E> set, Iterable<? extends D> dtoList) {
        if (set == null)
            set = new HashSet<E>();
        return mergeCollection(session, set, dtoList);
    }

    public static <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    /*    */Set<E> mergeSet(Set<E> set, Iterable<? extends D> dtoList) {
        if (set == null)
            set = new HashSet<E>();
        return mergeCollection((IMarshalSession) null, set, dtoList);
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

    public static <E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> //
    /*    */void mergeList(IMarshalSession session, E target, IPropertyAccessor<E, List<_e>> property,
            Iterable<? extends _d> dtoList) {

        List<_e> list = property.get(target);

        if (list == null)
            list = new ArrayList<_e>();

        if (session == null)
            list = mergeList(list, dtoList);
        else
            list = mergeList(session, list, dtoList);

        property.set(target, list);
    }

    public static <_E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> //
    /*    */void mergeList(_E target, IPropertyAccessor<_E, List<_e>> property, Iterable<? extends _d> dtoList) {
        mergeList(null, target, property, dtoList);
    }

    public static <E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> //
    /*    */void mergeList(IMarshalSession session, E target, String propertyName, Iterable<? extends _d> dtoList) {

        Class<E> targetType = (Class<E>) target.getClass();

        IPropertyAccessor<E, List<_e>> property;
        try {
            property = BeanPropertyAccessor.access(targetType, propertyName);
        } catch (IntrospectionException e) {
            // XXX Error message?
            throw new IllegalUsageException(e.getMessage(), e);
        }

        mergeList(session, target, property, dtoList);
    }

    public static <E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> //
    /*    */void mergeList(E target, String propertyName, Iterable<? extends _d> dtoList) {
        mergeList(null, target, propertyName, dtoList);
    }

    public static <E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> //
    /*    */void mergeSet(IMarshalSession session, E target, IPropertyAccessor<E, Set<_e>> property,
            Iterable<? extends _d> dtoList) {

        Set<_e> set = property.get(target);

        if (set == null)
            set = new HashSet<_e>();

        if (session == null)
            set = mergeSet(set, dtoList);
        else
            set = mergeSet(session, set, dtoList);

        property.set(target, set);
    }

    public static <E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> //
    /*    */void mergeSet(E target, IPropertyAccessor<E, Set<_e>> property, Iterable<? extends _d> dtoList) {
        mergeSet(null, target, property, dtoList);
    }

    public static <E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> //
    /*    */void mergeSet(IMarshalSession session, E target, String propertyName, Iterable<? extends _d> dtoList) {

        Class<E> targetType = (Class<E>) target.getClass();

        IPropertyAccessor<E, Set<_e>> property;
        try {
            property = BeanPropertyAccessor.access(targetType, propertyName);
        } catch (IntrospectionException e) {
            // XXX Error message?
            throw new IllegalUsageException(e.getMessage(), e);
        }

        mergeSet(session, target, property, dtoList);
    }

    public static <E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> //
    /*    */void mergeSet(E target, String propertyName, Iterable<? extends _d> dtoList) {
        mergeSet(null, target, propertyName, dtoList);
    }

}
