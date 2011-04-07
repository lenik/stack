package com.bee32.plover.inject.spring;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextBuilder {

    public static ApplicationContext buildSelfHostedContext1(Class<?> clazz) {
        AnnotationConfigApplicationContext annContext = null;
        // Find all annotated config classes by ImportUtil.
        Class<?>[] configClasses = ImportUtil.flatten(clazz);
        if (configClasses.length != 0) {
            annContext = new AnnotationConfigApplicationContext(configClasses);
            // include/exclude?AnnotationConfigApplicationContext
            // annContext.scan("com.bee32");
        }

        // Find all context config xmls by ContextConfigUtil.
        String[] locations = ContextConfigurationUtil.getContextConfigurationLocationArray(clazz);
        ClassPathXmlApplicationContext xmlContext = new ClassPathXmlApplicationContext(locations, annContext);
        return xmlContext;
    }

    public static ApplicationContext buildSelfHostedContext(Class<?> clazz) {
        ApplicationContext appContext;

        // Find all context config xmls by ContextConfigUtil.
        String[] locations = ContextConfigurationUtil.getContextConfigurationLocationArray(clazz);
        ClassPathXmlApplicationContext xmlContext = new ClassPathXmlApplicationContext(locations);
        appContext = xmlContext;

        AnnotationConfigApplicationContext annContext = null;
        // Find all annotated config classes by ImportUtil.
        Class<?>[] configClasses = ImportUtil.flatten(clazz);
        if (configClasses.length != 0) {
            annContext = new AnnotationConfigApplicationContext(configClasses);
            annContext.setParent(xmlContext);
            // annContext.refresh();
            appContext = annContext;
        }

        return appContext;
    }

    /**
     * @return self.
     */
    public static <T> T create(Class<T> beanClass) {
        if (beanClass == null)
            throw new NullPointerException("clazz");

        ApplicationContext context = buildSelfHostedContext(beanClass);
        AutowireCapableBeanFactory beanFactory = context.getAutowireCapableBeanFactory();
        return beanFactory.createBean(beanClass);
    }

    /**
     * @return self.
     */
    public static <T> T selfWire(T bean) {
        if (bean == null)
            throw new NullPointerException("bean");

        ApplicationContext context = buildSelfHostedContext(bean.getClass());
        AutowireCapableBeanFactory beanFactory = context.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(bean);
        return bean;
    }

}
