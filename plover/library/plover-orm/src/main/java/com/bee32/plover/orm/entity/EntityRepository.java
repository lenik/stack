package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.free.IllegalUsageException;
import javax.free.ParseException;
import javax.free.ParserUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.BuildException;
import com.bee32.plover.arch.Repository;
import com.bee32.plover.arch.naming.INamedNode;
import com.bee32.plover.arch.naming.NamedNode;
import com.bee32.plover.arch.naming.ReverseLookupRegistry;
import com.bee32.plover.arch.operation.AbstractOperation;
import com.bee32.plover.arch.operation.IOperation;
import com.bee32.plover.arch.operation.IOperationContext;
import com.bee32.plover.arch.operation.OperationBuilder;
import com.bee32.plover.arch.util.IStruct;

public abstract class EntityRepository<E extends IEntity<K>, K extends Serializable>
        extends Repository<K, E>
        implements IEntityRepository<E, K> {

    static Logger logger = LoggerFactory.getLogger(EntityRepository.class);

    protected Class<? extends E> entityType;

    private INamedNode parentLocator;

    private List<E> normalSamples;
    private List<E> worseSamples;

    public EntityRepository(Class<E> instanceType, Class<K> keyType) {
        super(keyType, instanceType);
        init();
    }

    public EntityRepository(Class<E> instanceType, Class<? extends E> entityType, Class<K> keyType) {
        super(keyType, instanceType);
        this.entityType = entityType;
        init();
    }

    public EntityRepository(String name, Class<E> instanceType, Class<K> keyType) {
        super(name, keyType, instanceType);
        init();
    }

    public EntityRepository(String name, Class<E> instanceType, Class<? extends E> entityType, Class<K> keyType) {
        super(name, keyType, instanceType);
        this.entityType = entityType;
        init();
    }

    @SuppressWarnings("unchecked")
    void init() {
        if (this.entityType == null) {
            Class<?> entityType;
            try {
                entityType = deferEntityType(instanceType);
            } catch (ClassNotFoundException e) {
                throw new IllegalUsageException("No implementation type for " + instanceType);
            }

            if (!instanceType.isAssignableFrom(entityType))
                throw new IllegalUsageException("Incompatible implementation " + entityType + " for " + instanceType);

            this.entityType = (Class<? extends E>) entityType;
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

    protected void addNormalSample(E... samples) {
        for (E sample : samples) {
            addNormalSample(sample);
        }
        return;
    }

    protected void addNormalSample(E sample) {
        if (normalSamples == null) {
            synchronized (this) {
                if (normalSamples == null)
                    normalSamples = new ArrayList<E>();
            }
        }
        normalSamples.add(sample);
    }

    protected void addWorseSample(E sample) {
        if (worseSamples == null) {
            synchronized (this) {
                if (worseSamples == null)
                    worseSamples = new ArrayList<E>();
            }
        }
        worseSamples.add(sample);
    }

    @Override
    public Collection<E> getTransientSamples(boolean worseCase) {
        if (worseCase) {
            if (worseSamples != null)
                return worseSamples;
            else
                return Collections.emptyList();
        } else {
            if (normalSamples != null)
                return normalSamples;
            else
                return Collections.emptyList();
        }
    }

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
        return instanceType;
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
        return retrieve(parsedKey);
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
        builder.add(CreateOperation.instance);
        builder.add(UpdateOperation.instance);
        builder.add(DeleteOperation.instance);
    }

    static class CreateOperation
            extends AbstractOperation {

        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public Object execute(Object instance, IOperationContext context)
                throws Exception {
            IEntityRepository repo = (EntityRepository) instance;

            Object entity = repo.populate(context);

            repo.saveOrUpdate(entity);

            return entity;
        }

        static final CreateOperation instance = new CreateOperation();

    }

    static class UpdateOperation
            extends AbstractOperation {

        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public Object execute(Object instance, IOperationContext context)
                throws Exception {
            EntityRepository repo = (EntityRepository) instance;

            String keyString = context.getPath();
            Serializable key = repo.parseKey(keyString);

            Object entity = repo.retrieve(key);
            repo.populate(entity, context);

            repo.update(entity);

            return entity;
        }

        static final UpdateOperation instance = new UpdateOperation();

    }

    static class DeleteOperation
            extends AbstractOperation {

        @SuppressWarnings({ "rawtypes" })
        @Override
        public Object execute(Object instance, IOperationContext context)
                throws Exception {

            EntityRepository repo = (EntityRepository) instance;

            String keyString = context.getPath();
            Serializable key = repo.parseKey(keyString);

            repo.deleteByKey(key);

            return repo;
        }

        static final DeleteOperation instance = new DeleteOperation();

    }

}
