package com.bee32.icsf.principal.dso;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.dao.UserDao;
import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.orm.entity.EntityDao;
import com.bee32.plover.orm.entity.EntityDso;

@Transactional(readOnly = true)
public class UserDso
        extends EntityDso<User, Long, UserDto> {

    @Inject
    UserDao userDao;

    @Override
    protected EntityDao<User, Long> getEntityDao() {
        return userDao;
    }

    public UserDto getByName(String name) {
        return new UserDto(0, userDao.getByName(name));
    }

}
