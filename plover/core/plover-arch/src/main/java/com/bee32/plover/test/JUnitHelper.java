package com.bee32.plover.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import javax.free.AnnotationUtil;
import javax.free.IllegalUsageException;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JUnitHelper {

    static final Logger logger = LoggerFactory.getLogger(JUnitHelper.class);

    static interface Callback<T> {

        T callback()
                throws Throwable;

    }

    public static <T> T doJUnitLifecycle(Object instance, Callback<T> callback)
            throws Throwable {

        if (instance == null)
            throw new NullPointerException("instance");

        Class<? extends Object> clazz = instance.getClass();

        T retval;

        try {

            beforeClass(clazz);
            beforeMethod(clazz, instance);

            retval = callback.callback();

            afterMethod(clazz, instance);
            afterClass(clazz);

        } catch (ReflectiveOperationException e) {
            throw new IllegalUsageException(e);
        }

        return retval;
    }

    static <A extends Annotation> boolean filter(Method method, boolean staticCheck, Class<A> annotation) {

        int modifiers = method.getModifiers();

        if (staticCheck)
            if (!Modifier.isStatic(modifiers))
                return false;
        ;

        // TODO - JUnit allow non-public @BeforeClass??
        if (!Modifier.isPublic(modifiers))
            return false;

        A an = AnnotationUtil.getDeclaredAnnotation(method, annotation);
        if (an == null)
            return false;

        int params = method.getParameterTypes().length;
        if (params != 0)
            throw new IllegalUsageException("@" + annotation.getSimpleName() + " method should not have any parameter.");

        if (logger.isTraceEnabled())
            logger.trace("Filtered: @" + annotation.getSimpleName() + " " + method.getDeclaringClass().getSimpleName()
                    + "::" + method.getName());

        return true;
    }

    static Set<Class<?>> initializedClasses = new HashSet<Class<?>>();

    static synchronized void beforeClass(Class<?> clazz)
            throws ReflectiveOperationException {

        if (initializedClasses.contains(clazz))
            return;

        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null)
            beforeClass(superclass);

        for (Method method : clazz.getDeclaredMethods()) {
            if (filter(method, true, BeforeClass.class))
                method.invoke(null);
        }

        initializedClasses.add(clazz);
    }

    static synchronized void afterClass(Class<?> clazz)
            throws ReflectiveOperationException {

        if (!initializedClasses.contains(clazz))
            return;

        for (Method method : clazz.getDeclaredMethods()) {
            if (filter(method, true, AfterClass.class))
                method.invoke(null);
        }

        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null)
            afterClass(superclass);

        initializedClasses.remove(clazz);
    }

    static void beforeMethod(Class<?> clazz, Object obj)
            throws ReflectiveOperationException {

        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null)
            beforeMethod(superclass, obj);

        for (Method method : clazz.getDeclaredMethods()) {
            if (filter(method, false, Before.class))
                method.invoke(obj);
        }
    }

    static void afterMethod(Class<?> clazz, Object obj)
            throws ReflectiveOperationException {

        for (Method method : clazz.getDeclaredMethods()) {
            if (filter(method, false, After.class))
                method.invoke(obj);
        }

        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null)
            afterMethod(superclass, obj);

    }

    static class WrapIntereceptor
            implements MethodInterceptor {

        static Set<Class<? extends Annotation>> junitAnnotations;

        static {
            // TODO - typehierset? for interface inheritance?
            junitAnnotations = new HashSet<Class<? extends Annotation>>();
            junitAnnotations.add(BeforeClass.class);
            junitAnnotations.add(AfterClass.class);
            junitAnnotations.add(Before.class);
            junitAnnotations.add(After.class);
        }

        static boolean topLevel = false;

        @Override
        public Object intercept(final Object obj, final Method method, final Object[] args, final MethodProxy proxy)
                throws Throwable {

            boolean isJunitMethod = false;

            L: for (Annotation ann : method.getAnnotations()) {
                Class<?> annClass = ann.getClass();
                for (Class<?> junitAnnClass : junitAnnotations)
                    if (junitAnnClass.isAssignableFrom(annClass)) {
                        isJunitMethod = true;
                        break L;
                    }
            }

            if (isJunitMethod)
                return proxy.invokeSuper(obj, args);

            if (topLevel)
                return proxy.invokeSuper(obj, args);
            else {
                topLevel = true;

                try {
                    return JUnitHelper.doJUnitLifecycle(obj, new Callback<Object>() {

                        public Object callback()
                                throws Throwable {
                            return proxy.invokeSuper(obj, args);
                        }

                    });
                } finally {
                    topLevel = false;
                }
            }
        }

        static final WrapIntereceptor INSTANCE = new WrapIntereceptor();
    }

    public static <T> T createWrappedInstance(Class<T> targetClass) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(WrapIntereceptor.INSTANCE);
        Object instance = enhancer.create();
        return targetClass.cast(instance);
    }

}
