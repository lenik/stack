package com.bee32.sem.api;

import java.io.Serializable;

import org.springframework.beans.BeansException;

import com.bee32.plover.inject.ServiceTemplate;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.util.MixinnedDataAssembledContext;

@ServiceTemplate
public abstract class AbstractSalaryVariableProvider
        implements ISalaryVariableProvider {

    protected static class ctx
            extends MixinnedDataAssembledContext {
    }

    @SuppressWarnings("deprecation")
    protected static Object BEAN(String name)
            throws BeansException {
        return ctx.bean.getBean(name);
    }

    @SuppressWarnings("deprecation")
    protected static <T> T BEAN(String name, Class<T> requiredType)
            throws BeansException {
        return ctx.bean.getBean(name, requiredType);
    }

    @SuppressWarnings("deprecation")
    protected static <T> T BEAN(Class<T> requiredType)
            throws BeansException {
        return ctx.bean.getBean(requiredType);
    }

    @SuppressWarnings("deprecation")
    protected static <E extends Entity<? extends K>, K extends Serializable> //
    IEntityAccessService<E, K> DATA(Class<? extends E> entityType) {
        return ctx.data.access(entityType);
    }

}
