package com.bee32.icsf.principal.dao;

import com.bee32.icsf.principal.realm.RealmBean;
import com.bee32.plover.orm.entity.AbstractDao;

public class RealmDao
        extends AbstractDao<RealmBean, Integer> {

    public RealmDao() {
        super(RealmBean.class, Integer.class);
    }

}
