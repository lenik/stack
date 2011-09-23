package com.bee32.plover.inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@ComponentTemplate
public abstract class ActivatorBean
        implements
        // Don't extend AbstractActivatorService, to avoid of service template.
        // IActivatorService,
        ApplicationContextAware {

    static Logger logger = LoggerFactory.getLogger(ActivatorBean.class);

    protected ApplicationContext appctx;

    public ActivatorBean() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.appctx = applicationContext;
        this.activate();
    }

    public void activate() {
    }

    public void deactivate() {
    }

}
