package com.bee32.sem.sandbox;

import java.util.List;

public interface SelectableList<T>
        extends List<T>, Selectable<T> {

    int getSelectionIndex();

    void setSelectionIndex(int i);

}
