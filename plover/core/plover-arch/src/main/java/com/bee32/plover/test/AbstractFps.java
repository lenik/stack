package com.bee32.plover.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.bee32.plover.inject.ServiceTemplate;

@ServiceTemplate
public abstract class AbstractFps
        implements IFeaturePlayerSupport {

    protected Logger logger = LoggerFactory.getLogger(AbstractFps.class);

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void setup(Class<?> playerClass) {
        logger.debug("FPS setup for " + playerClass);
    }

    @Override
    public void teardown(Class<?> playerClass) {
        logger.debug("FPS teardown for " + playerClass);
    }

    @Override
    public void init(ApplicationContext appctx) {
        logger.debug("FPS init appctx: " + appctx);
    }

    @Override
    public void begin(Object player) {
        logger.debug("FPS iteration begin: " + player);
    }

    @Override
    public void end(Object player) {
        logger.debug("FPS iteration end: " + player);
    }

}
