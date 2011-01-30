package com.bee32.plover.test.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public abstract class JpaModule
        extends AbstractModule {

    private static final ThreadLocal<EntityManager> entityManagers;
    static {
        entityManagers = new ThreadLocal<EntityManager>();
    }

    public void configure() {
    }

    @Provides
    @Singleton
    public EntityManagerFactory provideEntityManagerFactory()
            throws Exception {
        return createEntityManagerFactory();
    }

    protected abstract EntityManagerFactory createEntityManagerFactory()
            throws Exception;

    @Provides
    public EntityManager provideEntityManager(EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = entityManagers.get();
        if (entityManager == null) {
            entityManagers.set(entityManager = entityManagerFactory.createEntityManager());
        }
        return entityManager;
    }
}
