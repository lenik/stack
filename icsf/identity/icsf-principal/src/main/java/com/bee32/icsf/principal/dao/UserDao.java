package com.bee32.icsf.principal.dao;

import com.bee32.icsf.principal.UserBean;
import com.bee32.plover.orm.entity.AbstractDao;

public class UserDao
        extends AbstractDao<UserBean, Long> {

    public UserDao() {
        super(UserBean.class, Long.class);
    }

}
