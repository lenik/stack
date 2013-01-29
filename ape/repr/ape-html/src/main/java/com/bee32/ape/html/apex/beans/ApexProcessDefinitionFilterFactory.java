package com.bee32.ape.html.apex.beans;

import javax.inject.Inject;

import org.activiti.explorer.ComponentFactories;
import org.activiti.explorer.ui.process.ProcessDefinitionFilterFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
// @PerSite
public class ApexProcessDefinitionFilterFactory
        extends ProcessDefinitionFilterFactory
        implements InitializingBean {

    @Inject
    ComponentFactories componentFactories;

    @Override
    public void afterPropertiesSet()
            throws Exception {
        setComponentFactories(componentFactories);
    }

}
