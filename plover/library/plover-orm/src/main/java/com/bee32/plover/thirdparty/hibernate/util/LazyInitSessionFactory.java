package com.bee32.plover.thirdparty.hibernate.util;

import org.hibernate.SessionFactory;

public abstract class LazyInitSessionFactory
        extends DelegatedSessionFactory {

    private static final long serialVersionUID = 1L;

    private transient SessionFactory sessionFactory;

    @Override
    protected SessionFactory getDelegate() {
        if (sessionFactory == null) {
            synchronized (this) {
                if (sessionFactory == null) {
                    try {
                        sessionFactory = newSessionFactory();
                    } catch (RuntimeException e) {
                        throw e;
                    } catch (Exception e) {
                        throw new RuntimeException(e.getMessage(), e);
                    }
                }
            }
        }
        return sessionFactory;
    }

    protected abstract SessionFactory newSessionFactory()
            throws Exception;

}
