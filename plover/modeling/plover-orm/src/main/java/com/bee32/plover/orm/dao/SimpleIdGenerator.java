package com.bee32.plover.orm.dao;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.free.IllegalUsageException;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.EntityUtil;

public class SimpleIdGenerator {

    private static final Map<Class<?>, Long> allSeqs = new HashMap<Class<?>, Long>();

    public static synchronized void reset(Class<?> entityType) {
        if (entityType == null)
            throw new NullPointerException("entityType");

        allSeqs.remove(entityType);
    }

    static synchronized long next(Class<?> entityType) {
        if (entityType == null)
            throw new NullPointerException("entityType");

        Long seq = allSeqs.get(entityType);
        if (seq == null)
            seq = 1L;
        else
            seq++;
        allSeqs.put(entityType, seq);
        return seq;
    }

    public static Serializable generate(Entity<?> _entity) {
        if (_entity == null)
            throw new NullPointerException("entity");

        @SuppressWarnings("unchecked")
        Entity<Serializable> entity = (Entity<Serializable>) _entity;

        Class<? extends Entity<?>> entityType = (Class<? extends Entity<?>>) entity.getClass();

        Method idGetter;
        try {
            idGetter = entityType.getMethod("getId");
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        GeneratedValue generatedValue = idGetter.getAnnotation(GeneratedValue.class);
        if (generatedValue == null)
            throw new IllegalUsageException("Not auto-generated entity: " + entityType);

        GenerationType strategy = generatedValue.strategy();
        // TABLE, SEQUENCE, AUTO, etc.
        switch (strategy) {
        case AUTO:
        case SEQUENCE:
            break;
        case TABLE:
            throw new UnsupportedOperationException("Couldn't simulate a TABLE-generator used by " + entityType);
        default:
        }

        Serializable id;
        // The generic idGetter.getReturnType() not work here.
        Class<?> idType = EntityUtil.getKeyType(entityType);
        if (idType == Integer.class) {
            long nextId = next(entityType);
            id = (int) nextId;

        } else if (idType == Long.class) {
            long nextId = next(entityType);
            id = (long) nextId;

        } else if (idType == String.class) {
            id = UUID.randomUUID().toString();
        } else {
            throw new UnsupportedOperationException("Couldn't auto generate id of type " + idType + " for "
                    + entityType);
        }

        EntityAccessor.setId(entity, id);
        return id;
    }

}
