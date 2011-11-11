package com.bee32.sem.test;

import javax.free.IPrintOut;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;

public class BeanDefinitions {

    public static void dump(IPrintOut out, ApplicationContext appctx) {
        if (!(appctx instanceof BeanDefinitionRegistry))
            return;
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) appctx;
        dump(out, registry);
    }

    public static void dump(IPrintOut out, BeanDefinitionRegistry registry) {
        for (String name : registry.getBeanDefinitionNames()) {
            String[] aliases = registry.getAliases(name);
            BeanDefinition definition = registry.getBeanDefinition(name);

            String[] attr = definition.attributeNames();
            definition.getAttribute(attr);
        }
    }
}
