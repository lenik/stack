package com.bee32.icsf.principal.dao;

import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.SampleStore;
import com.bee32.plover.orm.entity.AbstractDao;

public class RoleDao
        extends AbstractDao<Role, Long> {

    public RoleDao() {
        super(Role.class, Long.class);
    }

    {
        SampleStore sampleStore = SampleStore.getInstance();

        addNormalSample(//
                sampleStore.adminRole, //
                sampleStore.registeredRole //
        );
    }

}
