package com.bee32.ape.engine.beans;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.SessionFactory;

import com.bee32.ape.engine.identity.ApeGroupEntityManagerFactory;
import com.bee32.ape.engine.identity.ApeMembershipEntityManagerFactory;
import com.bee32.ape.engine.identity.ApeUserEntityManagerFactory;

public class ApeIdentityPec
        extends AbstractPec {

    @Override
    public void configure(ProcessEngineConfigurationImpl configuration) {
        List<SessionFactory> customSessionFactories = new ArrayList<>();
        customSessionFactories.add(new ApeUserEntityManagerFactory());
        customSessionFactories.add(new ApeGroupEntityManagerFactory());
        customSessionFactories.add(new ApeMembershipEntityManagerFactory());
        configuration.setCustomSessionFactories(customSessionFactories);
    }

}
