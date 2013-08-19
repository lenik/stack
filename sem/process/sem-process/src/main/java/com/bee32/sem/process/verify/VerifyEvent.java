package com.bee32.sem.process.verify;

import java.io.Serializable;
import java.util.Date;

import com.bee32.icsf.principal.User;

/**
 * 审核事件
 *
 * <p lang="en">
 * Verify Event
 */
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

    /**
     * 时间戳
     *
     * <p lang="en">
     * Timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 是否允许
     *
     * <p lang="en">
     * Allowed
     */
    public boolean isAllowed() {
        return isAllowed;
    }

    public void setAllowed(boolean isAllowed) {
        this.isAllowed = isAllowed;
    }

    /**
     * 用户
     *
     * <p lang="en">
     * User
     */
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 消息
     *
     * <p lang="en">
     * Message
     */
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
