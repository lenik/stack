package com.bee32.icsf.login;

import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.principal.UserDto;

public class InitSessionLoginAdapter
        extends LoginAdapter {

    @Override
    public void logIn(LoginEvent event) {
        UserDto user = DTOs.marshal(UserDto.class, //
                UserDto.GROUPS | UserDto.ROLES, //
                event.getUser());

        LoginInfo loginInfo = LoginInfo.getInstance();
        loginInfo.setInternalUser(event.user);
        loginInfo.setUser(user);
    }

    @Override
    public void logOut(LoginEvent event) {
        LoginInfo loginInfo = LoginInfo.getInstance();
        loginInfo.destroy();
    }

}
