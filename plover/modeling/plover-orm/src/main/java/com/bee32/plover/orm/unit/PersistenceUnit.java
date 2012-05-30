package com.bee32.plover.orm.unit;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

import javax.free.IllegalUsageException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.util.ClassCatalog;
import com.bee32.plover.arch.util.IPriority;

/**
 * ImportUnit is merged by PersistenceUnit implementation.
 */
public abstract class PersistenceUnit
        extends ClassCatalog
        implements IPriority {

    public static final String MAIN_UNIT = "main-unit";

    static Logger logger = LoggerFactory.getLogger(PersistenceUnit.class);

    protected static final int SYSTEM_PRIORITY = -1000;

    static Map<Class<?>, PersistenceUnit> instances = new HashMap<Class<?>, PersistenceUnit>();

    private int priority;
    private boolean postProcessed;

    public PersistenceUnit(ClassCatalog... imports) {
        super(imports);
    }

    public PersistenceUnit(String name, ClassCatalog... imports) {
        super(name, imports);
    }

    {
        if (instances.containsValue(this))
            throw new IllegalStateException("PUnit instance duplicated: " + this);
        instances.put(getClass(), this);
    }

    public static synchronized PersistenceUnit getInstance(Class<? extends PersistenceUnit> unitClass) {
        PersistenceUnit instance = instances.get(unitClass);
        if (instance == null) {
            try {
                instance = unitClass.newInstance();
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
            instances.put(unitClass, instance);
        }
        return instance;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = super.getClasses();
        return classes;
    }

    /**
     * Called by user when he selected this unit.
     */
    public void select() {
        if (!postProcessed) {
            synchronized (this) {
                if (!postProcessed) {
                    postProcessed = true;

                    logger.info("Post process persistence unit " + getName());

                    postProcess();
                }
            }
        }
    }

    void postProcess() {
        for (IPersistenceUnitPostProcessor postProcessor : ServiceLoader.load(IPersistenceUnitPostProcessor.class)) {
            logger.debug("    Post process by " + postProcessor);
            postProcessor.process(this);
        }
    }

    @Override
    protected void internalAssemble() {
        loadImports(getClass());
    }

    private void loadImports(Class<?> clazz) {
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null)
            loadImports(superclass);

        ImportUnit importUnitAnnotation = clazz.getAnnotation(ImportUnit.class);
        if (importUnitAnnotation != null) {
            Class<? extends PersistenceUnit>[] unitClasses = importUnitAnnotation.value();
            assert unitClasses != null;

            for (Class<? extends PersistenceUnit> unitClass : unitClasses) {
                PersistenceUnit importUnit;
                try {
                    importUnit = getInstance(unitClass);
                } catch (Exception e) {
                    throw new IllegalUsageException("Failed to instantiate imported unit " + unitClass, e);
                }
                _import(importUnit);
            }
        }
    }

    /**
     * Publish entity classes here.
     *
     * @see #add(Class)
     * @see #remove(Class)
     */
    @Override
    protected abstract void preamble();

    static final PersistenceUnit defaultUnit = new SimplePUnit("DEFAULT");

    public static PersistenceUnit getDefault() {
        return defaultUnit;
    }

}
