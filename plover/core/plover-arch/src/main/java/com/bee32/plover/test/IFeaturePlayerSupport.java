package com.bee32.plover.test;

import org.springframework.context.ApplicationContext;

import com.bee32.plover.arch.util.IOrdered;

public interface IFeaturePlayerSupport
        extends IOrdered {

    void setup(Class<?> playerClass);

    void teardown(Class<?> playerClass);

    void init(ApplicationContext appctx);

    void begin(Object player);

    void end(Object player);

}
