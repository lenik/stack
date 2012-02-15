package com.bee32.plover.arch.logging;

import java.io.Serializable;
import java.util.Date;

public class ExceptionLogEntry
        implements Serializable {

    private static final long serialVersionUID = 1L;

    Date date;
    Object message;
    Throwable exception;
    String[] strRep;

    public ExceptionLogEntry(Date date, Object message, Throwable exception, String[] strRep) {
        this.date = date;
        this.message = message;
        this.exception = exception;
        this.strRep = strRep;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public String[] getStrRep() {
        return strRep;
    }

    public void setStrRep(String[] strRep) {
        this.strRep = strRep;
    }

}
