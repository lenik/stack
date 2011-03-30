package com.bee32.icsf.access.alt;

import com.bee32.plover.orm.entity.AbstractDao;

public class R_ACEDao
        extends AbstractDao<R_ACE, Long> {

    public R_ACEDao() {
        super(R_ACE.class, Long.class);
    }

}
