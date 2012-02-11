package com.bee32.icsf.login;

import java.util.Set;

import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.orm.entity.IdUtils;
import com.bee32.plover.orm.util.DTOs;

/**
 * 将登录事件转换为 DTO 并记录到 session 中。
 */
public class SessionUserLoginListener
        extends AbstractLoginListener {

    @Override
    public void logIn(LoginEvent event) {
        User _user = event.getUser();
        UserDto user = DTOs.marshal(UserDto.class, UserDto.GROUPS | UserDto.ROLES, _user);

        SessionUser sessionUser = SessionUser.getInstance();
        // sessionUser.setInternalUser(_user);
        sessionUser.setUser(user);

        Set<Integer> imSet = IdUtils.getIdSet(_user.getImSet());
        Set<Integer> invSet = IdUtils.getIdSet(_user.getInvSet());
        sessionUser.setImSet(imSet);
        sessionUser.setInvSet(invSet);
    }

    @Override
    public void logOut(LoginEvent event) {
        SessionUser sessionUser = SessionUser.getInstance();
        sessionUser.destroy();
    }

}
