package com.bee32.plover.test;

public interface IFeaturePlayerSupport {

    void setup(Class<?> playerClass);

    void teardown(Class<?> playerClass);

    void begin(Object player);

    void end(Object player);

}
