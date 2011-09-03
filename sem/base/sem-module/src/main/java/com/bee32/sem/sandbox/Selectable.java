package com.bee32.sem.sandbox;

public interface Selectable<T> {

    T getSelection();

    void setSelection(T selection);

    boolean isSelected();

    void deselect();

}
