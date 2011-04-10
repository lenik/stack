package com.bee32.plover.test;

import javax.inject.Inject;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.inject.cref.ScanTestxContext;
import com.bee32.plover.inject.spring.ApplicationContextBuilder;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(loader = MyLoader.class)
@Import(ScanTestxContext.class)
public abstract class WiredTestCase
        extends AssembledTestCase<WiredTestCase>
        implements InitializingBean {

    @Inject
    protected ApplicationContext application;

    protected AutowireCapableBeanFactory beanFactory;

    public WiredTestCase() {
        prewire();
        ApplicationContextBuilder.selfWire(this);
        logger.debug("WiredTestCase wired");
    }

    protected void prewire() {
    }

    @Override
    public void afterPropertiesSet()
            throws Exception {
        logger.debug("WiredTestCase - afterPropertiesSet.");

        this.beanFactory = application.getAutowireCapableBeanFactory();
        applicationInitialized(application);
    }

    protected void applicationInitialized(ApplicationContext applicationContext) {
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

    public WiredTestCase wire()
            throws Exception {
        WiredTestCase unit = super.unit();
        return ApplicationContextBuilder.selfWire(unit);
    }

}
