package com.bee32.ape.engine.beans;

import javax.free.ObjectInfo;
import javax.inject.Inject;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.springframework.stereotype.Component;

import com.bee32.plover.site.scope.PerSite;

@Component
@PerSite
public class ApeProcessEngineFactoryBean
        extends ProcessEngineFactoryBean {

    private ProcessEngine instance;

    @Override
    public synchronized ProcessEngine getObject()
            throws Exception {
        if (instance == null)
            instance = super.getObject();
        return instance;
    }

    @Override
    public void destroy()
            throws Exception {
        super.destroy();
        instance = null;
    }

    @Inject
    @Override
    public void setProcessEngineConfiguration(ProcessEngineConfigurationImpl configuration) {
        System.out.println("Set PE-Config: " + ObjectInfo.getObjectId(configuration));
        super.setProcessEngineConfiguration(configuration);
    }

}
