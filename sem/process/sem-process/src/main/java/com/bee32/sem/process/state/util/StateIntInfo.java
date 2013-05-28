package com.bee32.sem.process.state.util;

public class StateIntInfo {

    final int value;
    String label;
    String description;

    public StateIntInfo(int value) {
        this.value = value;
    }

    public StateIntInfo(int value, String label, String description) {
        if (label == null)
            throw new NullPointerException("label");
        if (description == null)
            throw new NullPointerException("description");
        this.value = value;
        this.label = label;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "State " + value + ": " + label + "\n" + description;
    }

}
