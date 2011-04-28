package com.bee32.icsf.principal.service;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.dao.UserDao;
import com.bee32.plover.arch.EnterpriseService;

@Transactional(readOnly = true)
public class UserService
        extends EnterpriseService {

    @Inject
    UserDao userDao;

    public User findByName(String name) {
        return userDao.findByName(name);
    }

}
