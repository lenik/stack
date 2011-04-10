package com.bee32.icsf.principal.dao;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity.EntityDao;

public class UserDao
        extends EntityDao<User, Long> {

    public UserDao() {
        super(User.class, Long.class);
    }

}
