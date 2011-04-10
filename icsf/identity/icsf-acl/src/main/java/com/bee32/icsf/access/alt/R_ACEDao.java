package com.bee32.icsf.access.alt;

import com.bee32.plover.orm.entity.EntityDao;

public class R_ACEDao
        extends EntityDao<R_ACE, Long> {

    public R_ACEDao() {
        super(R_ACE.class, Long.class);
    }

}
