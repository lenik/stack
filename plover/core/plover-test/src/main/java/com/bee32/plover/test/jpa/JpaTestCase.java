package com.bee32.plover.test.jpa;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class JpaTestCase {

    protected final Injector injector;

    public JpaTestCase() {
        Module providerModule = getProviderModule();
        injector = Guice.createInjector(providerModule);
    }

    /**
     * Default using H2-Database.
     */
    protected Module getProviderModule() {
        return new JpaModule_H2();
    }

    protected final <T> T getInstance(Class<T> clazz) {
        return injector.getInstance(clazz);
    }

}
