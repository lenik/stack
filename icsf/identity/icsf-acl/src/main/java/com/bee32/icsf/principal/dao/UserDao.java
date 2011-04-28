package com.bee32.icsf.principal.dao;

import org.hibernate.criterion.Restrictions;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity.EntityDao;

public class UserDao
        extends EntityDao<User, Long> {

    public User getByName(String name) {
        User user = (User) getSession().createCriteria(User.class)//
                .add(Restrictions.eq("name", name)).uniqueResult();
        return user;
    }

}
