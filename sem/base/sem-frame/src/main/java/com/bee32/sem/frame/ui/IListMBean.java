package com.bee32.sem.frame.ui;

import java.util.List;

import com.bee32.plover.faces.utils.SelectableList;

public interface IListMBean<T> {

    List<T> getList();

    SelectableList<T> getSelectableList();

    void addElement(T element);

    boolean removeElement(T element);

    T getSelection();

    void setSelection(T selection);

    int getSelectedIndex();

    void setSelectedIndex(int selectedIndex);

    void removeSelection();

    boolean isCopyMode();

    void setCopyMode(boolean copyMode);

    int getCopyIndex();

    T getCopy();

    void showIndexForm();

    void showCreateForm();

    void showEditForm();

}
