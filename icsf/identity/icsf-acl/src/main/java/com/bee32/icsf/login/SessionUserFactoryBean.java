package com.bee32.icsf.login;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bee32.plover.inject.scope.PerSession;
import com.bee32.plover.ox1.principal.AbstractPrincipalDto;
import com.bee32.plover.ox1.principal.Principal;
import com.bee32.plover.ox1.principal.User;
import com.bee32.plover.ox1.principal.UserDto;

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

    public void setInternalUser(User user) {
        SessionUser.getInstance().setInternalUser(user);
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

    public List<AbstractPrincipalDto<? extends Principal>> getChain() {
        return SessionUser.getInstance().getChain();
    }

}
