package com.bee32.ape.engine.beans;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.SessionFactory;

import com.bee32.ape.engine.identity.ApeGroupEntityManagerFactory;
import com.bee32.ape.engine.identity.ApeMembershipEntityManagerFactory;
import com.bee32.ape.engine.identity.ApeUserEntityManagerFactory;
import com.bee32.ape.engine.identity.mem.MemoryActivitiDatabase;

public class ApeIdentityPec
        extends AbstractPec {

    MemoryActivitiDatabase memoryActivitiDatabase;

    public ApeIdentityPec() {
        memoryActivitiDatabase = new MemoryActivitiDatabase();
    }

    @Override
    public void configure(ProcessEngineConfigurationImpl configuration) {
        List<SessionFactory> customSessionFactories = new ArrayList<>();
        customSessionFactories.add(new ApeUserEntityManagerFactory(memoryActivitiDatabase));
        customSessionFactories.add(new ApeGroupEntityManagerFactory(memoryActivitiDatabase));
        customSessionFactories.add(new ApeMembershipEntityManagerFactory());
        configuration.setCustomSessionFactories(customSessionFactories);
    }

}
