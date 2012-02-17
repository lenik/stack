package com.bee32.icsf.login;

import java.util.List;
import java.util.Set;

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.PrincipalDto;
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
        UserDto user = DTOs.mref(UserDto.class, UserDto.GROUPS | UserDto.ROLES, _user);

        SessionUser sessionUser = SessionUser.getInstance();
        // sessionUser.setInternalUser(_user);
        sessionUser.setUser(user);

        if (_user != null) {
            Set<Principal> _imSet = _user.getImSet();
            Set<Principal> _invSet = _user.getImSet();
            Set<Integer> imIdSet = IdUtils.getIdSet(_imSet);
            Set<Integer> invIdSet = IdUtils.getIdSet(_invSet);
            sessionUser.setImIdSet(imIdSet);
            sessionUser.setInvIdSet(invIdSet);

            List<PrincipalDto> imSet = DTOs.mrefList(PrincipalDto.class, 0, _imSet);
            List<PrincipalDto> invSet = DTOs.mrefList(PrincipalDto.class, 0, _invSet);
            sessionUser.setImSet(imSet);
            sessionUser.setInvSet(invSet);
        }
    }

    @Override
    public void logOut(LoginEvent event) {
        SessionUser sessionUser = SessionUser.getInstance();
        sessionUser.destroy();
    }

}
