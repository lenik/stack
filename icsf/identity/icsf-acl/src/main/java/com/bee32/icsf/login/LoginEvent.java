package com.bee32.icsf.login;

import java.util.EventObject;

import com.bee32.plover.ox1.principal.User;

public class LoginEvent
        extends EventObject {

    private static final long serialVersionUID = 1L;

    User user;

    public LoginEvent(Object source, User user) {
        super(source);
        setUser(user);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
