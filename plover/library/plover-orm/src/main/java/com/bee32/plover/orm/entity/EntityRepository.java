package com.bee32.plover.orm.entity;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.free.IllegalUsageException;
import javax.free.ParseException;
import javax.free.ParserUtil;

import com.bee32.plover.arch.Repository;
import com.bee32.plover.arch.naming.INamedNode;
import com.bee32.plover.arch.naming.NamedNode;
import com.bee32.plover.arch.naming.ReverseLookupRegistry;
import com.bee32.plover.arch.operation.AbstractOperation;
import com.bee32.plover.arch.operation.IOperation;
import com.bee32.plover.arch.operation.IParameterMap;
import com.bee32.plover.arch.operation.OperationBuilder;

public abstract class EntityRepository<E extends IEntity<K>, K>
        extends Repository<K, E>
        implements IEntityRepository<E, K> {

    protected Class<? extends E> entityType;

    private INamedNode parentLocator;

    public EntityRepository(Class<E> instanceType, Class<K> keyType) {
        super(keyType, instanceType);
        init();
    }

    public EntityRepository(String name, Class<E> instanceType, Class<K> keyType) {
        super(name, keyType, instanceType);
        init();
    }

    @SuppressWarnings("unchecked")
    void init() {
        Class<?> entityType;
        try {
            entityType = deferEntityType(instanceType);
        } catch (ClassNotFoundException e) {
            throw new IllegalUsageException("No implementation type for " + instanceType);
        }

        if (!instanceType.isAssignableFrom(entityType))
            throw new IllegalUsageException("Incompatible implementation " + entityType + " for " + instanceType);

        this.entityType = (Class<? extends E>) entityType;

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
        return entity.getPrimaryKey();
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
        return instanceType;
    }

    @Override
    public Object getChild(String location) {
        return retrieve(location);
    }

    @Override
    public boolean hasChild(Object entity) {
        if (entity == null)
            return false;

        if (!instanceType.isInstance(entity))
            return false;

        return true;
    }

    @Override
    public String getChildName(Object entity) {
        E instance = instanceType.cast(entity);
        K key = instance.getPrimaryKey();
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
        for (K key : keys)
            keyStrings.add(String.valueOf(key));

        return keyStrings;
    }

    /**
     * @see NamedNode#getOperationMap()
     */
    private Map<String, IOperation> operationMap;

    @Override
    public Collection<IOperation> getOperations() {
        return getOperationMap().values();
    }

    @Override
    public IOperation getOperation(String name) {
        IOperation operation = getOperationMap().get(name);
        return operation;
    }

    public Map<String, IOperation> getOperationMap() {
        if (operationMap == null) {
            synchronized (this) {
                if (operationMap == null) {
                    OperationBuilder operationBuilder = new OperationBuilder();
                    buildOperation(operationBuilder);
                    operationMap = operationBuilder.getMap();
                }
            }
        }
        return operationMap;
    }

    protected void buildOperation(OperationBuilder builder) {
        this.delete(entity);
        this.update(entity);
        this.save(entity);
    }

    static class DeleteOperation
            extends AbstractOperation {

        @Override
        public Object execute(Object instance, IParameterMap parameters)
                throws Exception {
            EntityRepository<?, ?> repo = (EntityRepository<?, ?>) instance;

            String entityKey = (String) parameters.get(0);
            Object key = repo.parseKey(entityKey);

            repo.deleteByKey(key);
        }

    }

}
