package com.bee32.icsf.login;

import java.util.List;
import java.util.Set;

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

    public Set<Integer> getImIdSet() {
        return SessionUser.getInstance().getImIdSet();
    }

    public void setImIdSet(Set<Integer> imIdSet) {
        SessionUser.getInstance().setImIdSet(imIdSet);
    }

    public Set<Integer> getInvIdSet() {
        return SessionUser.getInstance().getInvIdSet();
    }

    public void setInvIdSet(Set<Integer> invIdSet) {
        SessionUser.getInstance().setInvIdSet(invIdSet);
    }

    public List<PrincipalDto> getImSet() {
        return SessionUser.getInstance().getImSet();
    }

    public void setImSet(List<PrincipalDto> imSet) {
        SessionUser.getInstance().setImSet(imSet);
    }

    public List<PrincipalDto> getInvSet() {
        return SessionUser.getInstance().getInvSet();
    }

    public void setInvSet(List<PrincipalDto> invSet) {
        SessionUser.getInstance().setInvSet(invSet);
    }

}
