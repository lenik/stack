package com.bee32.plover.test;

import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bee32.plover.inject.cref.ContextRef;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ //
/*            */"/com/bee32/plover/inject/cref/auto-context.xml",
/*            */"/com/bee32/plover/inject/cref/scan-testx-context.xml" })
public abstract class WiredTestCase
        extends AssembledTestCase
        implements ApplicationContextAware, InitializingBean {

    protected ApplicationContext application;
    protected AutowireCapableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.application = applicationContext;
        this.beanFactory = application.getAutowireCapableBeanFactory();
    }

    @Override
    public void afterPropertiesSet()
            throws Exception {
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

    protected void main() {
        ApplicationContext context = new ContextRef(getClass()).buildApplicationContext();
        AutowireCapableBeanFactory factory = context.getAutowireCapableBeanFactory();
        factory.autowireBean(this);
        this.run();
    }

    protected void run() {
    }

}
