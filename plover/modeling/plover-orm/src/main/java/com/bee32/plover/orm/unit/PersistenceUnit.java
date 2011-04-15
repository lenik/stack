package com.bee32.plover.orm.unit;

import java.util.Set;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.util.ClassCatalog;

/**
 * ImportUnit is merged by PersistenceUnit implementation.
 */
public abstract class PersistenceUnit
        extends ClassCatalog {

    public PersistenceUnit(ClassCatalog... imports) {
        super(imports);
    }

    public PersistenceUnit(String name, ClassCatalog... imports) {
        super(name, imports);
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
                    importUnit = unitClass.newInstance();
                } catch (Exception e) {
                    throw new IllegalUsageException("Failed to instantiate imported unit " + unitClass, e);
                }

                Set<Class<?>> importClasses = importUnit.getClasses();
                addAll(importClasses);
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
