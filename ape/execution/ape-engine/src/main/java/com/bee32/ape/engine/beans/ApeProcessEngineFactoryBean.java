package com.bee32.ape.engine.beans;

import javax.inject.Inject;

import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.springframework.stereotype.Component;

import com.bee32.plover.site.scope.PerSite;

@Component
@PerSite
public class ApeProcessEngineFactoryBean
        extends ProcessEngineFactoryBean {

    @Inject
    @Override
    public void setProcessEngineConfiguration(ProcessEngineConfigurationImpl processEngineConfiguration) {
        super.setProcessEngineConfiguration(processEngineConfiguration);
    }

}
