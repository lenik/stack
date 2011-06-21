package com.bee32.icsf.principal.dso;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.dao.UserDao;
import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.orm.entity.EntityDao;
import com.bee32.plover.orm.entity.EntityDso;
import com.bee32.plover.orm.util.DTOs;

@Transactional(readOnly = true)
public class UserDso
        extends EntityDso<User, Integer> {

    @Inject
    UserDao userDao;

    @Override
    protected EntityDao<User, Integer> getDao() {
        return userDao;
    }

    public UserDto getByName(String name) {
        User user = userDao.getByName(name);
        return DTOs.marshal(UserDto.class, 0, user);
    }

}
