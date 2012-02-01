package com.bee32.sem.frame.ui;

import java.io.Serializable;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;
import javax.faces.component.UIComponent;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.bee32.plover.faces.utils.FacesContextSupport;
import com.bee32.plover.faces.utils.FacesUILogger;
import com.bee32.plover.faces.utils.SelectableList;

@NotThreadSafe
public abstract class ListMBean<T>
        extends FacesContextSupport
        implements IListMBean<T>, Serializable {

    private static final long serialVersionUID = 1L;

    final Class<T> elementType;
    int selectedIndex;
    T copy;

    public ListMBean(Class<T> elementType) {
        if (elementType == null)
            throw new NullPointerException("elementType");
        this.elementType = elementType;
    }

    @Override
    public abstract List<T> getList();

    @Override
    public SelectableList<T> getSelectableList() {
        return SelectableList.decorate(getList());
    }

    @Override
    public void addElement(T element) {
        getList().add(element);
    }

    @Override
    public boolean removeElement(T element) {
        return getList().remove(element);
    }

    @Override
    public T getSelection() {
        List<T> list = getList();
        if (selectedIndex == -1)
            return null;
        if (selectedIndex >= list.size())
            return null;
        return list.get(selectedIndex);
    }

    @Override
    public void setSelection(T selection) {
        List<T> list = getList();
        int index = list.indexOf(selection);
        selectedIndex = index;
    }

    @Override
    public int getSelectedIndex() {
        return selectedIndex;
    }

    @Override
    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    @Override
    public void removeSelection() {
        if (selectedIndex != -1) {
            List<T> list = getList();
            if (selectedIndex < list.size())
                list.remove(selectedIndex);
            selectedIndex = -1;
        }
    }

    @Override
    public T getActiveObject() {
        return copy;
    }

    static DataTable findDataTable(UIComponent component) {
        while (component != null) {
            if (component instanceof DataTable)
                return ((DataTable) component);
            component = component.getParent();
        }
        return null;
    }

    public void rowSelect(SelectEvent event) {
        UIComponent component = event.getComponent();
        DataTable dataTable = findDataTable(component);
        Object smooth = dataTable.getAttributes().get("smooth");
    }

    public void rowUnselect(UnselectEvent event) {
    }

    @Override
    public void showIndexForm() {
        copy = null;
    }

    @Override
    public void showCreateForm() {
        try {
            copy = elementType.newInstance();
            addElement(copy);
        } catch (ReflectiveOperationException e) {
            new FacesUILogger(false).error("创建失败", copy);
        }
    }

    @Override
    public void showEditForm() {
        System.out.println("EDIT");
        copy = getSelection();
    }

    public static <T> ListMBean<T> fromEL(Object root, String property, Class<T> elementType) {
        return new ELListMBean<>(elementType, root, property);
    }

}
