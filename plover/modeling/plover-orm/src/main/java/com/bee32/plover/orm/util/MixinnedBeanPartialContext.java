package com.bee32.plover.orm.util;

import org.springframework.beans.BeansException;

import com.bee32.plover.arch.util.ApplicationContextPartialContext;
import com.bee32.plover.arch.util.BeanPartialContext;

/**
 * This partial context suggests the use of BEAN(...).
 */
public class MixinnedBeanPartialContext
        extends BeanPartialContext {

    public MixinnedBeanPartialContext(ApplicationContextPartialContext _appctx) {
        super(_appctx);
    }

    /**
     * {@inheritDoc}
     *
     * @deprecated This method is deprecated in mixin classes. Please use BEAN(...) instead.
     */
    @Deprecated
    @Override
    public Object getBean(String name)
            throws BeansException {
        return super.getBean(name);
    }

    /**
     * {@inheritDoc}
     *
     * @deprecated This method is deprecated in mixin classes. Please use BEAN(...) instead.
     */
    @Deprecated
    @Override
    public <T> T getBean(String name, Class<T> requiredType)
            throws BeansException {
        return super.getBean(name, requiredType);
    }

    /**
     * {@inheritDoc}
     *
     * @deprecated This method is deprecated in mixin classes. Please use BEAN(...) instead.
     */
    @Deprecated
    @Override
    public <T> T getBean(Class<T> requiredType)
            throws BeansException {
        return super.getBean(requiredType);
    }

}
