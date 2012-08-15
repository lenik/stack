package com.bee32.plover.arch.util;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

public class BeanPartialContext
        implements IPartialContext {

    final ApplicationContextPartialContext _appctx;

    public BeanPartialContext(ApplicationContextPartialContext _appctx) {
        if (_appctx == null)
            throw new NullPointerException("_appctx");
        this._appctx = _appctx;
    }

    public ApplicationContext getAppCtx() {
        return _appctx.getAppCtx();
    }

    /**
     * Return an instance, which may be shared or independent, of the specified bean.
     * <p>
     * This method allows a Spring BeanFactory to be used as a replacement for the Singleton or
     * Prototype design pattern. Callers may retain references to returned objects in the case of
     * Singleton beans.
     * <p>
     * Translates aliases back to the corresponding canonical bean name. Will ask the parent factory
     * if the bean cannot be found in this factory instance.
     *
     * @param name
     *            the name of the bean to retrieve
     * @return an instance of the bean
     * @throws NoSuchBeanDefinitionException
     *             if there is no bean definition with the specified name
     * @throws BeansException
     *             if the bean could not be obtained
     */
    public Object getBean(String name)
            throws BeansException {
        return _appctx.getAppCtx().getBean(name);
    }

    /**
     * Return an instance, which may be shared or independent, of the specified bean.
     * <p>
     * Behaves the same as {@link #getBean(String)}, but provides a measure of type safety by
     * throwing a BeanNotOfRequiredTypeException if the bean is not of the required type. This means
     * that ClassCastException can't be thrown on casting the result correctly, as can happen with
     * {@link #getBean(String)}.
     * <p>
     * Translates aliases back to the corresponding canonical bean name. Will ask the parent factory
     * if the bean cannot be found in this factory instance.
     *
     * @param name
     *            the name of the bean to retrieve
     * @param requiredType
     *            type the bean must match. Can be an interface or superclass of the actual class,
     *            or <code>null</code> for any match. For example, if the value is
     *            <code>Object.class</code>, this method will succeed whatever the class of the
     *            returned instance.
     * @return an instance of the bean
     * @throws NoSuchBeanDefinitionException
     *             if there's no such bean definition
     * @throws BeanNotOfRequiredTypeException
     *             if the bean is not of the required type
     * @throws BeansException
     *             if the bean could not be created
     */
    public <T> T getBean(String name, Class<T> requiredType)
            throws BeansException {
        return _appctx.getAppCtx().getBean(name, requiredType);
    }

    /**
     * Return the bean instances that match the given object type (including subclasses), judging
     * from either bean definitions or the value of <code>getObjectType</code> in the case of
     * FactoryBeans.
     * <p>
     * <b>NOTE: This method introspects top-level beans only.</b> It does <i>not</i> check nested
     * beans which might match the specified type as well.
     * <p>
     * Does consider objects created by FactoryBeans, which means that FactoryBeans will get
     * initialized. If the object created by the FactoryBean doesn't match, the raw FactoryBean
     * itself will be matched against the type.
     * <p>
     * Does not consider any hierarchy this factory may participate in. Use BeanFactoryUtils'
     * <code>beansOfTypeIncludingAncestors</code> to include beans in ancestor factories too.
     * <p>
     * Note: Does <i>not</i> ignore singleton beans that have been registered by other means than
     * bean definitions.
     * <p>
     * This version of getBeansOfType matches all kinds of beans, be it singletons, prototypes, or
     * FactoryBeans. In most implementations, the result will be the same as for
     * <code>getBeansOfType(type, true, true)</code>.
     * <p>
     * The Map returned by this method should always return bean names and corresponding bean
     * instances <i>in the order of definition</i> in the backend configuration, as far as possible.
     *
     * @param type
     *            the class or interface to match, or <code>null</code> for all concrete beans
     * @return a Map with the matching beans, containing the bean names as keys and the
     *         corresponding bean instances as values
     * @throws BeansException
     *             if a bean could not be created
     * @since 1.1.2
     * @see FactoryBean#getObjectType
     * @see BeanFactoryUtils#beansOfTypeIncludingAncestors(ListableBeanFactory, Class)
     */
    public <T> Map<String, T> getBeansOfType(Class<T> type)
            throws BeansException {
        return _appctx.getAppCtx().getBeansOfType(type);
    }

    /**
     * Return the bean instance that uniquely matches the given object type, if any.
     *
     * @param requiredType
     *            type the bean must match; can be an interface or superclass. {@literal null} is
     *            disallowed.
     *            <p>
     *            This method goes into {@link ListableBeanFactory} by-type lookup territory but may
     *            also be translated into a conventional by-name lookup based on the name of the
     *            given type. For more extensive retrieval operations across sets of beans, use
     *            {@link ListableBeanFactory} and/or {@link BeanFactoryUtils}.
     * @return an instance of the single bean matching the required type
     * @throws NoSuchBeanDefinitionException
     *             if there is not exactly one matching bean found
     * @since 3.0
     * @see ListableBeanFactory
     */
    public <T> T getBean(Class<T> requiredType)
            throws BeansException {
        return _appctx.getAppCtx().getBean(requiredType);
    }

    /**
     * Return an instance, which may be shared or independent, of the specified bean.
     * <p>
     * Allows for specifying explicit constructor arguments / factory method arguments, overriding
     * the specified default arguments (if any) in the bean definition.
     *
     * @param name
     *            the name of the bean to retrieve
     * @param args
     *            arguments to use if creating a prototype using explicit arguments to a static
     *            factory method. It is invalid to use a non-null args value in any other case.
     * @return an instance of the bean
     * @throws NoSuchBeanDefinitionException
     *             if there's no such bean definition
     * @throws BeanDefinitionStoreException
     *             if arguments have been given but the affected bean isn't a prototype
     * @throws BeansException
     *             if the bean could not be created
     * @since 2.5
     */
    public Object getBean(String name, Object... args)
            throws BeansException {
        return _appctx.getAppCtx().getBean(name, args);
    }

    /**
     * Return the bean instances that match the given object type (including subclasses), judging
     * from either bean definitions or the value of <code>getObjectType</code> in the case of
     * FactoryBeans.
     * <p>
     * <b>NOTE: This method introspects top-level beans only.</b> It does <i>not</i> check nested
     * beans which might match the specified type as well.
     * <p>
     * Does consider objects created by FactoryBeans if the "allowEagerInit" flag is set, which
     * means that FactoryBeans will get initialized. If the object created by the FactoryBean
     * doesn't match, the raw FactoryBean itself will be matched against the type. If
     * "allowEagerInit" is not set, only raw FactoryBeans will be checked (which doesn't require
     * initialization of each FactoryBean).
     * <p>
     * Does not consider any hierarchy this factory may participate in. Use BeanFactoryUtils'
     * <code>beansOfTypeIncludingAncestors</code> to include beans in ancestor factories too.
     * <p>
     * Note: Does <i>not</i> ignore singleton beans that have been registered by other means than
     * bean definitions.
     * <p>
     * The Map returned by this method should always return bean names and corresponding bean
     * instances <i>in the order of definition</i> in the backend configuration, as far as possible.
     *
     * @param type
     *            the class or interface to match, or <code>null</code> for all concrete beans
     * @param includeNonSingletons
     *            whether to include prototype or scoped beans too or just singletons (also applies
     *            to FactoryBeans)
     * @param allowEagerInit
     *            whether to initialize <i>lazy-init singletons</i> and <i>objects created by
     *            FactoryBeans</i> (or by factory methods with a "factory-bean" reference) for the
     *            type check. Note that FactoryBeans need to be eagerly initialized to determine
     *            their type: So be aware that passing in "true" for this flag will initialize
     *            FactoryBeans and "factory-bean" references.
     * @return a Map with the matching beans, containing the bean names as keys and the
     *         corresponding bean instances as values
     * @throws BeansException
     *             if a bean could not be created
     * @see FactoryBean#getObjectType
     * @see BeanFactoryUtils#beansOfTypeIncludingAncestors(ListableBeanFactory, Class, boolean,
     *      boolean)
     */
    public <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit)
            throws BeansException {
        return _appctx.getAppCtx().getBeansOfType(type, includeNonSingletons, allowEagerInit);
    }

    /**
     * Does this bean factory contain a bean with the given name? More specifically, is
     * {@link #getBean} able to obtain a bean instance for the given name?
     * <p>
     * Translates aliases back to the corresponding canonical bean name. Will ask the parent factory
     * if the bean cannot be found in this factory instance.
     *
     * @param name
     *            the name of the bean to query
     * @return whether a bean with the given name is defined
     */
    public boolean containsBean(String name) {
        return _appctx.getAppCtx().containsBean(name);
    }

    /**
     * Find all beans whose <code>Class</code> has the supplied
     * {@link java.lang.annotation.Annotation} type.
     *
     * @param annotationType
     *            the type of annotation to look for
     * @return a Map with the matching beans, containing the bean names as keys and the
     *         corresponding bean instances as values
     * @throws BeansException
     *             if a bean could not be created
     */
    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType)
            throws BeansException {
        return _appctx.getAppCtx().getBeansWithAnnotation(annotationType);
    }

    /**
     * Determine the type of the bean with the given name. More specifically, determine the type of
     * object that {@link #getBean} would return for the given name.
     * <p>
     * For a {@link FactoryBean}, return the type of object that the FactoryBean creates, as exposed
     * by {@link FactoryBean#getObjectType()}.
     * <p>
     * Translates aliases back to the corresponding canonical bean name. Will ask the parent factory
     * if the bean cannot be found in this factory instance.
     *
     * @param name
     *            the name of the bean to query
     * @return the type of the bean, or <code>null</code> if not determinable
     * @throws NoSuchBeanDefinitionException
     *             if there is no bean with the given name
     * @since 1.1.2
     * @see #getBean
     * @see #isTypeMatch
     */
    public Class<?> getType(String name)
            throws NoSuchBeanDefinitionException {
        return _appctx.getAppCtx().getType(name);
    }

}
