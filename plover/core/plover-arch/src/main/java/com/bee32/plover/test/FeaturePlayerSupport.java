package com.bee32.plover.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FeaturePlayerSupport
        implements IFeaturePlayerSupport {

    protected Logger logger = LoggerFactory.getLogger(FeaturePlayerSupport.class);

    @Override
    public void setup(Class<?> playerClass) {
        logger.debug("FPS setup for " + playerClass);
    }

    @Override
    public void teardown(Class<?> playerClass) {
        logger.debug("FPS teardown for " + playerClass);
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
