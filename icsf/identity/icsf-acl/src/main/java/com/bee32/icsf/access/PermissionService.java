package com.bee32.icsf.access;

import java.util.Collection;

import javax.inject.Inject;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;

import com.bee32.icsf.access.annotation.CheckpointDiscoverer;
import com.bee32.icsf.access.builtins.PointPermission;
import com.bee32.plover.arch.EnterpriseService;

@Lazy
public class PermissionService
        extends EnterpriseService
        implements InitializingBean {

    @Inject
    CheckpointDiscoverer checkpointDiscoverer;

    @Override
    public void afterPropertiesSet()
            throws Exception {
        checkpointDiscoverer.scan();
    }

    public Permission getPermission(String name) {
        return Permissions.getPermission(name);
    }

    public Collection<PointPermission> getCheckpoints() {
        return checkpointDiscoverer.getCheckpoints();
    }

}
