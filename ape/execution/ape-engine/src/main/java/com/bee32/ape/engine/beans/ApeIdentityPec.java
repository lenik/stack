package com.bee32.ape.engine.beans;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.SessionFactory;

import com.bee32.ape.engine.identity.ApeGroupEntityManager;
import com.bee32.ape.engine.identity.ApeGroupEntityManagerFactory;
import com.bee32.ape.engine.identity.ApeMembershipEntityManagerFactory;
import com.bee32.ape.engine.identity.ApeUserEntityManager;
import com.bee32.ape.engine.identity.ApeUserEntityManagerFactory;
import com.bee32.ape.engine.identity.icsf.IIcsfTypeMapping;
import com.bee32.ape.engine.identity.icsf.IcsfTypeMapping_G;
import com.bee32.ape.engine.identity.mem.MemoryActivitiDatabase;

public class ApeIdentityPec
        extends AbstractPec {

    MemoryActivitiDatabase memdb;
    IIcsfTypeMapping icsfTypeMapping;

    public ApeIdentityPec() {
        memdb = new MemoryActivitiDatabase();
        icsfTypeMapping = new IcsfTypeMapping_G();
    }

    @Override
    public void configure(ProcessEngineConfigurationImpl configuration) {
        ApeUserEntityManager userEntityManager = new ApeUserEntityManager(icsfTypeMapping, memdb);
        ApeGroupEntityManager groupEntityManager = new ApeGroupEntityManager(icsfTypeMapping, memdb);

        List<SessionFactory> customSessionFactories = new ArrayList<>();
        customSessionFactories.add(new ApeUserEntityManagerFactory(userEntityManager));
        customSessionFactories.add(new ApeGroupEntityManagerFactory(groupEntityManager));
        customSessionFactories.add(new ApeMembershipEntityManagerFactory(icsfTypeMapping, memdb));
        configuration.setCustomSessionFactories(customSessionFactories);
    }

}
