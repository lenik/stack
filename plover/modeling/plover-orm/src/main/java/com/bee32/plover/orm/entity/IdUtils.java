package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.bee32.plover.orm.util.EntityDto;

public class IdUtils {

    public static <K extends Serializable> K getId(Entity<? extends K> entity) {
        K id;
        if (entity == null)
            id = null;
        else
            id = entity.getId();
        return id;
    }

    public static <K extends Serializable> K getId(EntityDto<?, ? extends K> dto) {
        K id;
        if (dto == null)
            id = null;
        else
            id = dto.getId();
        return id;
    }

    public static <K extends Serializable> List<K> getIdList(Iterable<? extends Entity<? extends K>> entities) {
        if (entities == null)
            return null;
        List<K> idList = new ArrayList<K>();
        for (Entity<? extends K> entity : entities)
            idList.add(getId(entity));
        return idList;
    }

    public static <K extends Serializable> Set<K> getIdSet(Iterable<? extends Entity<? extends K>> entities) {
        if (entities == null)
            return null;
        Set<K> idSet = new LinkedHashSet<K>();
        for (Entity<? extends K> entity : entities)
            idSet.add(getId(entity));
        return idSet;
    }

    public static <K extends Serializable> List<K> getDtoIdList(Iterable<? extends EntityDto<?, ? extends K>> dtos) {
        if (dtos == null)
            return null;
        List<K> idList = new ArrayList<K>();
        for (EntityDto<?, ? extends K> dto : dtos)
            idList.add(getId(dto));
        return idList;
    }

    public static <K extends Serializable> Set<K> getDtoIdSet(Iterable<? extends EntityDto<?, ? extends K>> dtos) {
        if (dtos == null)
            return null;
        Set<K> idSet = new LinkedHashSet<K>();
        for (EntityDto<?, ? extends K> dto : dtos)
            idSet.add(getId(dto));
        return idSet;
    }

}
