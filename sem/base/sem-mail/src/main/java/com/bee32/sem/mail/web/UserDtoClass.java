package com.bee32.sem.mail.web;

import java.util.List;

import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;

public class UserDtoClass
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    List<UserDto> userList;

    UserDtoClass(){
        List<User> users = serviceFor(User.class).list();
        userList = DTOs.marshalList(UserDto.class, users);
    }

    public List<UserDto> getUserList() {
        return userList;
    }

    public void setUserList(List<UserDto> userList) {
        this.userList = userList;
    }

}
