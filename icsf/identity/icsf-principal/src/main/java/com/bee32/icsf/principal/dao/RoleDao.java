package com.bee32.icsf.principal.dao;

import com.bee32.icsf.principal.RoleBean;
import com.bee32.plover.orm.entity.AbstractDao;

public class RoleDao
        extends AbstractDao<RoleBean, Long> {

    public RoleDao() {
        super(RoleBean.class, Long.class);
    }

}
