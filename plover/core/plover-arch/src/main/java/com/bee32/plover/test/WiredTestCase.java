package com.bee32.plover.test;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ //
/*            */"/com/bee32/plover/inject/cref/auto-context.xml",
/*            */"/com/bee32/plover/inject/cref/scan-testx-context.xml" })
public abstract class WiredTestCase
        extends Assert {

    @Inject
    protected ApplicationContext appContext;

    protected AutowireCapableBeanFactory beanFactory;

    public void afterPropertiesSet() {
        beanFactory = appContext.getAutowireCapableBeanFactory();
    }

    protected <T> T createBean(Class<T> beanClass)
            throws BeansException {
        return beanFactory.createBean(beanClass);
    }

    protected <T> T createBean(Class<T> beanClass, int autowireMode, boolean dependencyCheck)
            throws BeansException {
        Object bean = beanFactory.createBean(beanClass, autowireMode, dependencyCheck);
        return beanClass.cast(bean);
    }

}
