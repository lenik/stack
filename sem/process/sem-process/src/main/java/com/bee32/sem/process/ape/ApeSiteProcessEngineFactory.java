package com.bee32.sem.process.ape;

import javax.inject.Inject;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Lazy;

import com.bee32.plover.site.scope.PerSite;

@PerSite
@Lazy
public class ApeSiteProcessEngineFactory
        implements FactoryBean<ProcessEngine> {

    @Inject
    ProcessEngineConfiguration configuration;

    ProcessEngine processEngine;

    @Override
    public Class<?> getObjectType() {
        return ProcessEngine.class;
    }

    @Override
    public synchronized ProcessEngine getObject()
            throws Exception {
        if (processEngine == null)
            processEngine = configuration.buildProcessEngine();
        return processEngine;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
