package com.bee32.ape.html.apex.beans;

import javax.inject.Inject;

import org.activiti.explorer.ComponentFactories;
import org.activiti.explorer.ui.ComponentFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.bee32.plover.site.scope.PerSite;

@Component
@PerSite(reason = "appctx in use in the modified ProcessEngines.DEFAULT.")
public class ApexComponentFactories
        extends ComponentFactories
        implements InitializingBean {

    @Inject
    ApplicationContext appctx;

    @Inject
    ApexConfig config;

    @Override
    public void afterPropertiesSet()
            throws Exception {
        setEnvironment(config.getEnvironment());
    }

    @Override
    public <T> ComponentFactory<T> get(Class<? extends ComponentFactory<T>> clazz) {
        ComponentFactory<T> componentFactory = super.get(clazz);
        if (componentFactory == null) {
            componentFactory = appctx.getBean(clazz);
        }
        return componentFactory;
    }

}
