package com.bee32.sem.process.verify.builtin;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

import com.bee32.icsf.principal.User;
import com.bee32.plover.arch.Component;
import com.bee32.sem.process.verify.VerifyState;

public class AllowState
        extends VerifyState {

    private static final long serialVersionUID = 1L;

    private boolean isAllowed;
    private User user;
    private String message;

    public AllowState() {
    }

    public AllowState(boolean isAllowed, User user) {
        if (user == null)
            throw new NullPointerException("user");

        this.isAllowed = isAllowed;
        this.user = user;
    }

    public boolean isAllowed() {
        return isAllowed;
    }

    public void setAllowed(boolean isAllowed) {
        this.isAllowed = isAllowed;
    }

    @ManyToOne(optional = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(length = 200)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    protected int hashCodeSpecific() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    @Override
    protected boolean equalsSpecific(Component obj) {
        AllowState other = (AllowState) obj;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }

    @Override
    public String getStateMessage() {
        String verb = isAllowed ? "[允许]" : "[拒绝]";
        return verb + " 由： " + user;
    }

}
