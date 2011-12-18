package com.bee32.sem.process.verify;

import com.bee32.plover.ox1.principal.User;

public class AllowedByContext
        extends VerifyContext
        implements ISingleVerifier {

    private boolean allowed;
    private User user;
    private String message;

    @Override
    public boolean isAccepted() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    @Override
    public User getVerifier() {
        return user;
    }

    public void setVerifier(User user) {
        this.user = user;
    }

    @Override
    public String getRejectedReason() {
        return message;
    }

    public void setRejectReason(String message) {
        this.message = message;
    }

}
