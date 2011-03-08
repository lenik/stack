package com.bee32.plover.test;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("component-scan.xml")
public abstract class WiredAssembledTestCase
        extends AssembledTestCase
        implements InitializingBean {

    @Inject
    private ApplicationContext context;

    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    public void afterPropertiesSet() {
        // context.getAutowireCapableBeanFactory();
    }

    protected <T> T createBean(Class<T> beanClass)
            throws BeansException {
        return autowireCapableBeanFactory.createBean(beanClass);
    }

    protected <T> T createBean(Class<T> beanClass, int autowireMode, boolean dependencyCheck)
            throws BeansException {
        Object bean = autowireCapableBeanFactory.createBean(beanClass, autowireMode, dependencyCheck);
        return beanClass.cast(bean);
    }

}
