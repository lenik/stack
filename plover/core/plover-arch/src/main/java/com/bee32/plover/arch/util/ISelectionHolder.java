package com.bee32.plover.arch.util;

import java.util.List;

public interface ISelectionHolder {

    boolean isSelected();

    List<?> getSelection();

    void setSelection(List<?> selection);

    Object getSingleSelection();

    void setSingleSelection(Object singleSelection);

    Object[] getSelectionArray();

    void setSelectionArray(Object... selectionArray);

    List<?> getSelection(Class<?>... interfaces);

    void addSelectionChangeListener(ISelectionChangeListener listener);

    void removeSelectionChangeListener(ISelectionChangeListener listener);

}
