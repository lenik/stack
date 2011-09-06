package com.bee32.icsf.login;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.plover.ox1.principal.AbstractPrincipalDto;
import com.bee32.plover.ox1.principal.Principal;
import com.bee32.plover.ox1.principal.User;
import com.bee32.plover.ox1.principal.UserDto;

@Component("loginInfo")
@Scope("session")
public class LoginInfoBean
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public User getInternalUserOpt() {
        return LoginInfo.getInstance().getInternalUserOpt();
    }

    public final User getInternalUser()
            throws LoginException {
        return LoginInfo.getInstance().getInternalUser();
    }

    public UserDto getUserOpt() {
        return LoginInfo.getInstance().getUserOpt();
    }

    public final UserDto getUser()
            throws LoginException {
        return LoginInfo.getInstance().getUser();
    }

    public List<AbstractPrincipalDto<? extends Principal>> getChain() {
        return LoginInfo.getInstance().getChain();
    }

}
