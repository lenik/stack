package com.bee32.icsf.access.alt;

import com.bee32.plover.orm.entity.AbstractDao;

public class R_ACLDao
        extends AbstractDao<R_ACL, Long> {

    public R_ACLDao() {
        super(R_ACL.class, Long.class);
    }

}
