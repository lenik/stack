package com.bee32.plover.faces.test;

import java.io.Serializable;

public class SimpleEntry
        implements Serializable {

    private static final long serialVersionUID = 1L;

    String key;
    String label;

    public SimpleEntry() {
    }

    public SimpleEntry(String key, String label) {
        this.key = key;
        this.label = label;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
