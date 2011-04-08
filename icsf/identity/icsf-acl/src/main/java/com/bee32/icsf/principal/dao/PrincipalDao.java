package com.bee32.icsf.principal.dao;

import com.bee32.icsf.principal.Principal;
import com.bee32.plover.orm.entity.AbstractDao;

public class PrincipalDao
        extends AbstractDao<Principal, Long> {

    public PrincipalDao() {
        super(Principal.class, Long.class);
    }

    @Override
    protected Class<?> deferEntityType(Class<?> clazz)
            throws ClassNotFoundException {
        return super.deferEntityType(clazz);
    }

}
