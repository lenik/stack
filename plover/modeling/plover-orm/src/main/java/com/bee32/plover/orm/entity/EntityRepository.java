package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.free.IllegalUsageException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.BuildException;
import com.bee32.plover.arch.util.IStruct;

public abstract class EntityRepository<E extends IEntity<K>, K extends Serializable>
        extends TreeRepository<E, K>
        implements IEntityRepository<E, K> {

    static Logger logger = LoggerFactory.getLogger(EntityRepository.class);

    protected Class<? extends E> entityType;

    public EntityRepository() {
        super();
    }

    public EntityRepository(String name) {
        super(name);
    }

    {
        if (this.entityType == null) {
            Class<?> entityType;
            try {
                entityType = deferEntityType(valueType);
            } catch (ClassNotFoundException e) {
                throw new IllegalUsageException("No implementation type for " + valueType);
            }

            if (!valueType.isAssignableFrom(entityType))
                throw new IllegalUsageException("Incompatible implementation " + entityType + " for " + valueType);

            @SuppressWarnings("unchecked")
            Class<? extends E> entityClass = (Class<? extends E>) entityType;

            this.entityType = entityClass;
        }
    }

    public Class<? extends E> getEntityType() {
        return entityType;
    }

    protected Class<?> deferEntityType(Class<?> clazz)
            throws ClassNotFoundException {

        int modifiers = clazz.getModifiers();
        if (!Modifier.isAbstract(modifiers))
            return clazz;

        String typeName = clazz.getName();
        int lastDot = typeName.lastIndexOf('.');
        String prefix;
        String simpleName;
        if (lastDot == -1) {
            prefix = "";
            simpleName = typeName;
        } else {
            prefix = typeName.substring(0, lastDot + 1);
            simpleName = typeName.substring(lastDot + 1);
        }

        String entityClassName = null;
        if (simpleName.length() >= 2) {
            char a = simpleName.charAt(0);
            char b = simpleName.charAt(1);
            if (a == 'I' && Character.isUpperCase(b))
                entityClassName = simpleName.substring(1);
            else
                entityClassName = simpleName + "Impl";
        } else
            entityClassName = simpleName + "Impl";

        entityClassName = prefix + entityClassName;

        return Class.forName(entityClassName, true, clazz.getClassLoader());
    }

    @Override
    public K getKey(E entity) {
        return entity.getId();
    }

    @Override
    public Collection<K> keys() {
        List<? extends E> list = list();
        List<K> keys = new ArrayList<K>();
        for (E entity : list) {
            K key = getKey(entity);
            keys.add(key);
        }
        return keys;
    }

    @Override
    public E populate(IStruct struct)
            throws BuildException {
        E entity;
        try {
            entity = entityType.newInstance();
        } catch (Exception e) {
            throw new BuildException(e);
        }

        populate(entity, struct);

        return entity;
    }

    @Override
    public abstract K save(E entity);

    @Override
    public abstract void update(E entity);

    @Override
    public abstract void refresh(E entity);

}
