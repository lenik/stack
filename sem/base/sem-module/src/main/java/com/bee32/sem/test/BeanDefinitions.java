package com.bee32.sem.test;

import javax.free.IIndentedOut;
import javax.free.IndentedOutImpl;
import javax.free.Stdio;
import javax.free.StringArray;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.zkoss.lang.Strings;

public class BeanDefinitions {

    public static void dump(ApplicationContext appctx) {
        dump(new IndentedOutImpl(Stdio.cout), appctx);
    }

    public static void dump(BeanDefinitionRegistry registry) {
        dump(new IndentedOutImpl(Stdio.cout), registry);
    }

    public static void dump(IIndentedOut out, ApplicationContext appctx) {
        out.println("application-context: " + appctx.getDisplayName());
        out.enter();
        try {
            BeanDefinitionRegistry registry = null;

            if (appctx instanceof BeanDefinitionRegistry)
                registry = (BeanDefinitionRegistry) appctx;
            else {
                AutowireCapableBeanFactory beanFactory = appctx.getAutowireCapableBeanFactory();
                if (beanFactory instanceof BeanDefinitionRegistry)
                    registry = (BeanDefinitionRegistry) beanFactory;
            }

            if (registry != null) {
                dump(out, registry);
            } else {
                out.println("Not a bean definition registry.");
            }
        } finally {
            out.leave();
        }

        ApplicationContext parent = appctx.getParent();
        if (parent == null)
            return;

        out.println();
        out.print("parent-");
        dump(out, parent);
    }

    static boolean verbose = false;

    public static void dump(IIndentedOut out, BeanDefinitionRegistry registry) {
        for (String name : registry.getBeanDefinitionNames()) {
            String[] aliases = registry.getAliases(name);
            BeanDefinition definition = registry.getBeanDefinition(name);
            String beanClass = definition.getBeanClassName();
            int role = definition.getRole();

            if (!verbose && role != BeanDefinition.ROLE_APPLICATION)
                continue;

            out.print("bean: " + name);
            if (aliases.length != 0)
                out.print(" (aliases: " + StringArray.join(", ", aliases) + ")");
            out.print(": " + beanClass);
            out.println();
            out.enter();

            String description = definition.getDescription();
            if (!Strings.isEmpty(description))
                out.println("description: " + description);

            if (verbose) {
                String resourceDescription = definition.getResourceDescription();
                if (!Strings.isEmpty(resourceDescription))
                    out.println("resource-description: " + resourceDescription);

                Object source = definition.getSource();
                if (source != null)
                    out.println("source: " + source);

                String roleName = null;
                switch (role) {
                case BeanDefinition.ROLE_APPLICATION:
                    roleName = "application";
                    break;
                case BeanDefinition.ROLE_INFRASTRUCTURE:
                    roleName = "infrastructure";
                    break;
                case BeanDefinition.ROLE_SUPPORT:
                    roleName = "support";
                    break;
                }
                if (roleName != null)
                    out.println("role: " + roleName);
            }

            String scope = definition.getScope();
            if (!Strings.isEmpty(scope))
                out.println("scope: " + scope);

            String parentName = definition.getParentName();
            if (parentName != null)
                out.println("parent: " + parentName);

            String factoryBeanName = definition.getFactoryBeanName();
            String factoryMethodName = definition.getFactoryMethodName();
            if (factoryBeanName != null) {
                out.println("factory-bean: " + factoryBeanName);
                if (factoryMethodName != null)
                    out.println("factory-method: " + factoryMethodName);
            }

            String[] depends = definition.getDependsOn();
            if (depends != null && depends.length != 0)
                out.println("depends: " + StringArray.join(", ", depends));

            for (String attributeName : definition.attributeNames()) {
                Object attributeValue = definition.getAttribute(attributeName);
                out.println("attribute " + attributeName + ": " + attributeValue);
            }

            // BeanDefinition orig = definition.getOriginatingBeanDefinition();
            MutablePropertyValues propertyValues = definition.getPropertyValues();
            for (PropertyValue property : propertyValues.getPropertyValueList()) {
                String propertyName = property.getName();
                Object propertyValue = property.getValue();
                out.println("property " + propertyName + ": " + propertyValue);
            }

            out.leave();
        }
    }

}
