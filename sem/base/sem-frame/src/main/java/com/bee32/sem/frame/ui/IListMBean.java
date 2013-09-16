package com.bee32.sem.frame.ui;

import java.util.List;

import javax.free.CreateException;

import com.bee32.plover.faces.utils.SelectableList;

public interface IListMBean<T> {

    List<T> getList();

    SelectableList<T> getSelectableList();

    T createElement()
            throws CreateException;

    void addElement(T element);

    boolean removeElement(T element);

    List<T> getSelection();

    void setSelection(List<T> selection);

    T getLastSelection();

    void setLastSelection(T selection);

    int getLastIndex();

    int[] getSelectedIndexes();

    void setSelectedIndexes(int... selectedIndexes);

    void removeSelection();

    boolean isCopyMode();

    void setCopyMode(boolean copyMode);

    int getOpenedIndex();

    Object getOpenedObject();

    void showIndexForm();

    void showCreateForm();

    void showEditForm();

    boolean isEditing();

}
