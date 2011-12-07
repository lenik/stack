package com.bee32.plover.inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.access.BootstrapException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Eagerly initialized services.
 */
@ComponentTemplate
public abstract class InitializingService
        implements
        // Don't extend AbstractActivatorService, to avoid of service template.
        // IActivatorService,
        ApplicationContextAware {

    static Logger logger = LoggerFactory.getLogger(InitializingService.class);

    protected ApplicationContext appctx;
    private boolean inited;

    public InitializingService() {
        // logger.info("Initializing service ctor: " + this);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.appctx = applicationContext;
        try {
            this.initialize();
        } catch (Exception e) {
            throw new BootstrapException(e.getMessage(), e);
        }
    }

    public final synchronized void initialize()
            throws Exception {
        if (inited)
            return;
        _initialize();
        inited = true;
    }

    protected void _initialize()
            throws Exception {

    }

    public void uninitialize()
            throws Exception {
    }

}
