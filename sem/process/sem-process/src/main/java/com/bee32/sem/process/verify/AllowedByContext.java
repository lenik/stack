package com.bee32.sem.process.verify;

import com.bee32.icsf.principal.User;

public class AllowedByContext
        extends VerifyContext
        implements IAllowedByContext {

    private boolean allowed;
    private User user;
    private String message;

    @Override
    public boolean isAllowed() {
        return allowed;
    }

    @Override
    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

}
