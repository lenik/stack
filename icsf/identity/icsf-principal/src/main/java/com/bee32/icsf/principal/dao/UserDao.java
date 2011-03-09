package com.bee32.icsf.principal.dao;

import org.springframework.stereotype.Service;

import com.bee32.icsf.principal.UserBean;
import com.bee32.plover.orm.entity.AbstractDao;

@Service
public class UserDao
        extends AbstractDao<UserBean, Long> {

    public UserDao() {
        super(UserBean.class, Long.class);
    }

    {

    }

}
