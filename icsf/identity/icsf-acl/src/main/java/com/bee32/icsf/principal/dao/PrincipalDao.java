package com.bee32.icsf.principal.dao;

import com.bee32.icsf.principal.Principal;
import com.bee32.plover.orm.entity.EntityDao;

public class PrincipalDao
        extends EntityDao<Principal, Long> {

    @Override
    protected Class<?> deferEntityType(Class<?> clazz)
            throws ClassNotFoundException {
        return super.deferEntityType(clazz);
    }

}
