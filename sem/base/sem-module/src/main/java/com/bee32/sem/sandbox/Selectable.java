package com.bee32.sem.sandbox;

public interface Selectable<T> {

    T getSelection();

    void setSelection(T selection);

    /**
     * Only meaningful if the list does not contain <code>null</code> element.
     */
    boolean isSelected();

    /**
     * Only meaningful if the list does not contain <code>null</code> element.
     */
    void deselect();

}
