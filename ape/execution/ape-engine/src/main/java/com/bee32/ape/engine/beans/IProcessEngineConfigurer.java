package com.bee32.ape.engine.beans;

import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.spring.SpringProcessEngineConfiguration;

import com.bee32.plover.arch.util.IPriority;

public interface IProcessEngineConfigurer
        extends IPriority {

    void configure(ProcessEngineConfigurationImpl configuration);

    void configureSpring(SpringProcessEngineConfiguration configuration);

}
