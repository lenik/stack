package com.bee32.icsf.principal.dao;

import com.bee32.icsf.principal.realm.Realm;
import com.bee32.plover.orm.entity.EntityDao;

public class RealmDao
        extends EntityDao<Realm, Integer> {

    public RealmDao() {
        super(Realm.class, Integer.class);
    }

}
