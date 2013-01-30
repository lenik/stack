package com.bee32.ape.engine.beans;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.SessionFactory;

import com.bee32.ape.engine.identity.ApeGroupManagerFactory;
import com.bee32.ape.engine.identity.ApeUserManagerFactory;

public class ApeIdentityPec
        extends AbstractPec {

    @Override
    public void processEngineConfigure(ProcessEngineConfigurationImpl configuration) {
        List<SessionFactory> customSessionFactories = new ArrayList<>();
        customSessionFactories.add(new ApeUserManagerFactory());
        customSessionFactories.add(new ApeGroupManagerFactory());
        configuration.setCustomSessionFactories(customSessionFactories);
    }

}
