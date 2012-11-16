package com.bee32.plover.util;

public class VariableEntry {

    Object value;
    String description;

    public VariableEntry(Object value) {
        this.value = value;
    }

    public VariableEntry(Object value, String description) {
        this.value = value;
        this.description = description;
    }

}
