package com.bee32.plover.arch.util;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.springframework.beans.BeansException;
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

    public Object getBean(String name)
            throws BeansException {
        return _appctx.getAppCtx().getBean(name);
    }

    public <T> T getBean(String name, Class<T> requiredType)
            throws BeansException {
        return _appctx.getAppCtx().getBean(name, requiredType);
    }

    public <T> Map<String, T> getBeansOfType(Class<T> type)
            throws BeansException {
        return _appctx.getAppCtx().getBeansOfType(type);
    }

    public <T> T getBean(Class<T> requiredType)
            throws BeansException {
        return _appctx.getAppCtx().getBean(requiredType);
    }

    public Object getBean(String name, Object... args)
            throws BeansException {
        return _appctx.getAppCtx().getBean(name, args);
    }

    public <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit)
            throws BeansException {
        return _appctx.getAppCtx().getBeansOfType(type, includeNonSingletons, allowEagerInit);
    }

    public boolean containsBean(String name) {
        return _appctx.getAppCtx().containsBean(name);
    }

    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType)
            throws BeansException {
        return _appctx.getAppCtx().getBeansWithAnnotation(annotationType);
    }

    public Class<?> getType(String name)
            throws NoSuchBeanDefinitionException {
        return _appctx.getAppCtx().getType(name);
    }

}
