package com.bee32.icsf.login;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.inject.scope.PerSession;

@Component("sessionUser")
@PerSession
public class SessionUserFactoryBean {

    public User getInternalUserOpt() {
        return SessionUser.getInstance().getInternalUserOpt();
    }

    public final User getInternalUser()
            throws LoginException {
        return SessionUser.getInstance().getInternalUser();
    }

    public UserDto getUserOpt() {
        return SessionUser.getInstance().getUserOpt();
    }

    public final UserDto getUser()
            throws LoginException {
        return SessionUser.getInstance().getUser();
    }

    public void setUser(UserDto user) {
        SessionUser.getInstance().setUser(user);
    }

    public List<PrincipalDto> getChain() {
        return SessionUser.getInstance().getChain();
    }

}
