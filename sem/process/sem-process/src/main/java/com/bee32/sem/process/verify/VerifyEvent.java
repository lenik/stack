package com.bee32.sem.process.verify;

import java.io.Serializable;
import java.util.Date;

import com.bee32.icsf.principal.User;

public class VerifyEvent
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date timestamp;
    private boolean isAllowed;
    private User user;
    private String message;

    public VerifyEvent() {
    }

    public VerifyEvent(boolean isAllowed, User user) {
        if (user == null)
            throw new NullPointerException("user");

        this.timestamp = new Date();
        this.isAllowed = isAllowed;
        this.user = user;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isAllowed() {
        return isAllowed;
    }

    public void setAllowed(boolean isAllowed) {
        this.isAllowed = isAllowed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        String verb = isAllowed ? "[允许]" : "[拒绝]";
        return verb + " 由： " + user;
    }

}
