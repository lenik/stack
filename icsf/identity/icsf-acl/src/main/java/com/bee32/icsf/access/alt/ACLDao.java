package com.bee32.icsf.access.alt;

import com.bee32.plover.orm.entity.AbstractDao;

public class ACLDao
        extends AbstractDao<ACLBean, Long> {

    public ACLDao() {
        super(ACLBean.class, Long.class);
    }

}
