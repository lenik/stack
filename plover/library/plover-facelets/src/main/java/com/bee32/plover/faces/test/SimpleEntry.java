package com.bee32.plover.faces.test;

import java.io.Serializable;
import java.util.Map.Entry;

public class SimpleEntry
        implements Entry<String, String>, Serializable {

    private static final long serialVersionUID = 1L;

    String key;
    String label;

    public SimpleEntry() {
    }

    public SimpleEntry(String key, String label) {
        this.key = key;
        this.label = label;
    }

    @Override
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

    @Override
    public String getValue() {
        return getLabel();
    }

    @Override
    public String setValue(String value) {
        String old = getValue();
        setLabel(value);
        return old;
    }

    @Override
    public String toString() {
        return key + ": " + label;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((label == null) ? 0 : label.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SimpleEntry other = (SimpleEntry) obj;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (label == null) {
            if (other.label != null)
                return false;
        } else if (!label.equals(other.label))
            return false;
        return true;
    }

}
