package com.bee32.sem.frame.ui;

import java.util.List;

import com.bee32.plover.faces.utils.FacesContextSupport;
import com.bee32.plover.faces.utils.FacesUILogger;
import com.bee32.plover.faces.utils.SelectableList;

public class ListModel<T>
        extends FacesContextSupport {

    final List<T> list;
    final Class<T> elementType;
    T selection;
    T activeObject;

    public ListModel(List<T> list, Class<T> elementType) {
        if (list == null)
            throw new NullPointerException("list");
        if (elementType == null)
            throw new NullPointerException("elementType");
        this.list = list;
        this.elementType = elementType;
    }

    public List<T> getList() {
        return list;
    }

    public SelectableList<T> getSelectableList() {
        return SelectableList.decorate(getList());
    }

    public void addElement(T element) {
        list.add(element);
    }

    public void removeElement(T element) {
        list.remove(element);
    }

    public T getSelection() {
        return selection;
    }

    public void setSelection(T selection) {
        this.selection = selection;
    }

    public void removeSelection() {
        removeElement(selection);
    }

    public T getActiveObject() {
        return activeObject;
    }

    public void showIndexForm() {
        activeObject = null;
    }

    public void showCreateForm() {
        try {
            activeObject = elementType.newInstance();
            addElement(activeObject);
        } catch (ReflectiveOperationException e) {
            new FacesUILogger(false).error("创建失败", activeObject);
        }
    }

    public void showEditForm() {
        activeObject = selection;
    }

}
