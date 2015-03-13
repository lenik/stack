package com.bee32.zebra.oa.calendar;

import java.util.Date;

import com.tinylily.model.base.CoObject;
import com.tinylily.model.base.security.User;

public class LogEntry
        extends CoObject {

    private static final long serialVersionUID = 1L;

    Class<?> categoryClass;
    Long id;
    User op;
    String message;
    Date date;

    public Class<?> getCategoryClass() {
        return categoryClass;
    }

    public void setCategoryClass(Class<?> categoryClass) {
        this.categoryClass = categoryClass;
    }

    public Object getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOp() {
        return op;
    }

    public void setOp(User op) {
        this.op = op;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
