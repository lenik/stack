package com.bee32.icsf.principal.dao;

import org.springframework.stereotype.Service;

import com.bee32.icsf.principal.PrincipalBean;
import com.bee32.plover.orm.entity.AbstractDao;

@Service
public class PrincipalDao
        extends AbstractDao<PrincipalBean, Long> {

    public PrincipalDao() {
        super(PrincipalBean.class, Long.class);
    }

    {
        // addNormalSample(WorldPrincipal.getInstance());
    }

}
