package com.bee32.icsf.login;

import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.orm.util.DTOs;

/**
 * 将登录事件转换为 DTO 并记录到 session 中。
 */
public class SessionUserLoginListener
        extends AbstractLoginListener {

    @Override
    public void logIn(LoginEvent event) {
        UserDto user = DTOs.marshal(UserDto.class, //
                UserDto.GROUPS | UserDto.ROLES, //
                event.getUser());

        SessionUser sessionUser = SessionUser.getInstance();
        sessionUser.setInternalUser(event.user);
        sessionUser.setUser(user);
    }

    @Override
    public void logOut(LoginEvent event) {
        SessionUser sessionUser = SessionUser.getInstance();
        sessionUser.destroy();
    }

}
