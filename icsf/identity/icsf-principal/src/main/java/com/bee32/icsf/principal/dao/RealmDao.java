package com.bee32.icsf.principal.dao;

import com.bee32.icsf.principal.realm.Realm;
import com.bee32.plover.orm.entity.AbstractDao;

public class RealmDao
        extends AbstractDao<Realm, Integer> {

    public RealmDao() {
        super(Realm.class, Integer.class);
    }

}
