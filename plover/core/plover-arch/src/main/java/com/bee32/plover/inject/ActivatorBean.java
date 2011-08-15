package com.bee32.plover.inject;

import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@ComponentTemplate
public abstract class ActivatorBean
        implements
        // Not extending AbstractActivatorService, to avoid of service template.
        // IActivatorService, //
        ApplicationContextAware {

    static Logger logger = LoggerFactory.getLogger(ActivatorBean.class);

    protected ApplicationContext appctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.appctx = applicationContext;

        activateStaticService(appctx);

        this.activate();
    }

    public void activate() {
    }

    public void deactivate() {
    }

    static boolean staticServiceActivated;

    static synchronized void activateStaticService(ApplicationContext appctx) {
        if (staticServiceActivated)
            return;

        for (IActivatorService service : ServiceLoader.load(IActivatorService.class)) {
            logger.info("Activate system service: " + service.getClass().getName());

            service.setApplicationContext(appctx);
            service.activate();
        }
        staticServiceActivated = true;
    }

}
