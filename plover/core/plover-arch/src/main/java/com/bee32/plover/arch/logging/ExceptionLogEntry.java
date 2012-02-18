package com.bee32.plover.arch.logging;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ExceptionLogEntry
        implements Serializable {

    private static final long serialVersionUID = 1L;

    Date date;
    Object message;
    Throwable exception;
    String[] strRep;
    Map<String, Object> attributes = new TreeMap<>();

    public ExceptionLogEntry(Date date, Object message, Throwable exception, String... strRep) {
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

    String[] getStrRep() {
        return strRep;
    }

    void setStrRep(String[] strRep) {
        this.strRep = strRep;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public Object getAttribute(String attributeName) {
        return attributes.get(attributeName);
    }

    public void setAttribute(String attributeName, Object attributeValue) {
        attributes.put(attributeName, attributeValue);
    }

    static final Map<String, String> attributeDisplayNameMap;
    static {
        attributeDisplayNameMap = new HashMap<String, String>();
    }

    public static void registerAttribute(String name, String displayName) {
        attributeDisplayNameMap.put(name, displayName);
    }

    public static String getDisplayName(String attributeName) {
        String displayName = attributeDisplayNameMap.get(attributeName);
        if (displayName == null)
            return attributeName;
        else
            return displayName;
    }

}
