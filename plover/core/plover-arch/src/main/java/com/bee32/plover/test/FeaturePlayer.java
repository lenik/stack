package com.bee32.plover.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

import com.bee32.plover.inject.spring.ApplicationContextBuilder;

public abstract class FeaturePlayer<T> {

    static Set<IFeaturePlayerSupport> supports;

    static {
        supports = new HashSet<IFeaturePlayerSupport>();
        for (IFeaturePlayerSupport support : ServiceLoader.load(IFeaturePlayerSupport.class)) {
            supports.add(support);
        }
    }

    private void fireSetup(Class<?> playerClass) {
        for (IFeaturePlayerSupport support : supports)
            support.setup(playerClass);
    }

    private void fireTeardown(Class<?> playerClass) {
        for (IFeaturePlayerSupport support : supports)
            support.teardown(playerClass);
    }

    private void fireBegin(Object player) {
        for (IFeaturePlayerSupport support : supports)
            support.begin(player);
    }

    private void fireEnd(Object player) {
        for (IFeaturePlayerSupport support : supports)
            support.end(player);
    }

    protected static BufferedReader stdin;
    static {
        stdin = new BufferedReader(new InputStreamReader(System.in));
    }

    protected void mainLoop()
            throws IOException {

        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] typeArgs = genericSuperclass.getActualTypeArguments();

        @SuppressWarnings("unchecked")
        Class<T> playerClass = (Class<T>) typeArgs[0];

        fireSetup(playerClass);

        ApplicationContext applicationContext = ApplicationContextBuilder.buildSelfHostedContext(playerClass);
        AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();

        while (true) {
            try {
                T player = beanFactory.createBean(playerClass);

                fireBegin(player);
                {
                    main(player);
                }
                fireEnd(player);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.err.println("--------------------------------------------------");
            System.err.println("Press enter to try again");
            String line = stdin.readLine();

            if (line == null || line.trim().equals("q"))
                break;
        }

        fireTeardown(playerClass);
    }

    protected abstract void main(T player)
            throws Exception;

}
