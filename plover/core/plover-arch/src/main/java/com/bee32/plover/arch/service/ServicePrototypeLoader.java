package com.bee32.plover.arch.service;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.free.IllegalUsageException;
import javax.free.URLResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServicePrototypeLoader {

    static Logger logger = LoggerFactory.getLogger(ServicePrototypeLoader.class);

    public static <T> Iterable<Class<? extends T>> load(Class<T> serviceBaseType, boolean includeAbstract)
            throws IOException, ClassNotFoundException {
        // Or using Context class loader?...
        ClassLoader classLoader = serviceBaseType.getClassLoader();

        return load(serviceBaseType, includeAbstract, classLoader);
    }

    public static <T> Iterable<Class<? extends T>> load(Class<T> serviceBaseType, boolean includeAbstract,
            ClassLoader classLoader)
            throws IOException, ClassNotFoundException {
        String resourceName = "META-INF/prototypes/" + serviceBaseType.getName();

        Enumeration<URL> resources = classLoader.getResources(resourceName);

        Set<Class<? extends T>> serviceClasses = new LinkedHashSet<Class<? extends T>>();

        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();

            for (String line : new URLResource(url).forRead().listLines()) {
                String className = line.trim();

                if (className.isEmpty())
                    continue;

                if (className.startsWith("#"))
                    continue;

                /*
                 * Optimize: Maybe another class loader different to the resource discoverer should
                 * be used.
                 */
                Class<?> serviceClass;
                try {
                    serviceClass = Class.forName(className, true, classLoader);
                } catch (ExceptionInInitializerError e) {
                    logger.error("Failed to resolve service class: " + className, e);
                    throw e;
                }

                if (!serviceBaseType.isAssignableFrom(serviceClass))
                    throw new IllegalUsageException(String.format("Invalid service class %s for %s.", //
                            className, serviceBaseType.getName()));

                if (Modifier.isAbstract(serviceClass.getModifiers()))
                    if (!includeAbstract)
                        continue;

                @SuppressWarnings("unchecked")
                Class<? extends T> casted = (Class<? extends T>) serviceClass;
                serviceClasses.add(casted);
            }
        }

        return serviceClasses;
    }

}
