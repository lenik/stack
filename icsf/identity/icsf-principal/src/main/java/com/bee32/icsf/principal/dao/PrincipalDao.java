package com.bee32.icsf.principal.dao;

import com.bee32.icsf.principal.IPrincipal;
import com.bee32.icsf.principal.PrincipalBean;
import com.bee32.plover.orm.entity.AbstractDao;

public class PrincipalDao
        extends AbstractDao<IPrincipal, Long> {

    public PrincipalDao() {
        super(IPrincipal.class, PrincipalBean.class, Long.class);
    }

    @Override
    protected Class<?> deferEntityType(Class<?> clazz)
            throws ClassNotFoundException {
        return super.deferEntityType(clazz);
    }

    {
        // addNormalSample(WorldPrincipal.getInstance());
    }

}
