package com.bee32.plover.site;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

public class RequestEntry
        implements Serializable {

    private static final long serialVersionUID = 1L;

    final Date date = new Date();
    final String requestURI;
    Map<String, Object> attributes = new TreeMap<>();

    public RequestEntry(HttpServletRequest req) {
        requestURI = req.getRequestURI();
        RecManager.getInstance().completeEntry(this);
    }

    public Date getDate() {
        return date;
    }

    public String getRequestURI() {
        return requestURI;
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
