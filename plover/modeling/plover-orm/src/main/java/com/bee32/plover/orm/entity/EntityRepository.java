package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.free.IllegalUsageException;
import javax.free.ParseException;
import javax.free.ParserUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.BuildException;
import com.bee32.plover.arch.Repository;
import com.bee32.plover.arch.naming.INamedNode;
import com.bee32.plover.arch.naming.ReverseLookupRegistry;
import com.bee32.plover.arch.util.IStruct;

public abstract class EntityRepository<E extends IEntity<K>, K extends Serializable>
        extends Repository<K, E>
        implements IEntityRepository<E, K>, INamedNode {

    static Logger logger = LoggerFactory.getLogger(EntityRepository.class);

    protected Class<? extends E> entityType;

    private INamedNode parentLocator;

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
        ReverseLookupRegistry.getInstance().register(this);
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
    public Collection<? extends K> listKeys() {
        Collection<? extends E> list = list();
        ArrayList<K> keys = new ArrayList<K>(list.size());
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

    // --o INamedNode

    protected K parseKey(String keyString)
            throws ParseException {
        if (keyType == String.class)
            return keyType.cast(keyString);

        if (keyType == Integer.class) {
            Integer integerId = Integer.valueOf(keyString);
            return keyType.cast(integerId);
        }

        if (keyType == Long.class) {
            Long longId = Long.valueOf(keyString);
            return keyType.cast(longId);
        }

        return ParserUtil.parse(keyType, keyString);
    }

    protected String formatKey(K key) {
        return String.valueOf(key);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public INamedNode getParent() {
        return parentLocator;
    }

    public void setParent(INamedNode parentLocator) {
        this.parentLocator = parentLocator;
    }

    @Override
    public Class<?> getChildType() {
        return valueType;
    }

    @Override
    public Object getChild(String location) {
        K parsedKey;
        try {
            parsedKey = parseKey(location);
        } catch (ParseException e) {
            if (logger.isDebugEnabled())
                logger.warn("Bad location token: " + location + ", for " + this, e);
            return null;
        }
        return get(parsedKey);
    }

    @Override
    public boolean hasChild(Object entity) {
        if (entity == null)
            return false;

        if (!entityType.isInstance(entity))
            return false;

        return true;
    }

    @Override
    public String getChildName(Object entity) {
        E instance = entityType.cast(entity);
        K key = instance.getId();
        String keyLocation = formatKey(key);
        return keyLocation;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<String> getChildNames() {
        Collection<? extends K> keys = listKeys();

        if (keyType == String.class)
            return (Collection<String>) keys;

        List<String> keyStrings = new ArrayList<String>(keys.size());
        for (K key : keys) {
            String keyString = formatKey(key);
            keyStrings.add(keyString);
        }

        return keyStrings;
    }

    @Override
    public Iterable<?> getChildren() {
        return list();
    }

}
