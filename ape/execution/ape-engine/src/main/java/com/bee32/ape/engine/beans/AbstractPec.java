package com.bee32.ape.engine.beans;

import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.bee32.plover.inject.ServiceTemplate;

@ServiceTemplate
public abstract class AbstractPec
        implements IProcessEngineConfigurer, ApplicationContextAware {

    protected ApplicationContext appctx;

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.appctx = applicationContext;
    }

    @Override
    public final void configureSpring(SpringProcessEngineConfiguration configuration) {
        if (appctx == null)
            throw new IllegalStateException("appctx wasn't set.");
        this.configure((ProcessEngineConfigurationImpl) configuration);
        configureSpringSpecific(configuration);
    }

    protected void configureSpringSpecific(SpringProcessEngineConfiguration configuration) {
    }

}
