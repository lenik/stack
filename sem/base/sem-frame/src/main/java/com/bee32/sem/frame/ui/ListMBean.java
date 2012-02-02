package com.bee32.sem.frame.ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;
import javax.faces.component.UIComponent;
import javax.free.UnexpectedException;

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
    int selectedIndex = -1;
    boolean copyMode;
    int copyIndex = -1;
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
        setSelectedIndex(index);
    }

    @Override
    public int getSelectedIndex() {
        return selectedIndex;
    }

    @Override
    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
        if (copyMode)
            if (selectedIndex != copyIndex) {
                copyIndex = -1;
                copy = null;
            }
    }

    @Override
    public void removeSelection() {
        if (selectedIndex != -1) {
            List<T> list = getList();
            if (selectedIndex < list.size())
                list.remove(selectedIndex);
            if (copyIndex == selectedIndex) {
                copyIndex = -1;
                copy = null;
            }
            selectedIndex = -1;
        }
    }

    @Override
    public boolean isCopyMode() {
        return copyMode;
    }

    @Override
    public void setCopyMode(boolean copyMode) {
        this.copyMode = copyMode;
    }

    static final Method cloneMethod;
    static {
        try {
            cloneMethod = Object.class.getDeclaredMethod("clone");
            cloneMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new UnexpectedException(e.getMessage(), e);
        }
    }

    protected T copyObject(T value) {
        if (!copyMode)
            return value;
        // System.out.println("Copy: " + value);
        if (value == null)
            return null;
        if (value instanceof Cloneable) {
            try {
                return (T) cloneMethod.invoke(value);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("Failed to clone " + value, e);
            }
        }
        if (value instanceof Serializable) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                new ObjectOutputStream(out).writeObject(value);
                byte[] data = out.toByteArray();
                ByteArrayInputStream in = new ByteArrayInputStream(data);
                return (T) new ObjectInputStream(in).readObject();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            } catch (ClassNotFoundException e) {
                throw new UnexpectedException(e.getMessage(), e);
            }
        }
        throw new UnsupportedOperationException("Can't copy object of " + value.getClass());
    }

    @Override
    public int getCopyIndex() {
        return copyIndex;
    }

    @Override
    public T getCopy() {
        return copy;
    }

    public T getActiveObject() {
        return getCopy();
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
        Boolean smooth = (Boolean) dataTable.getAttributes().get("smooth");
        if (smooth) {
            showEditForm();
        }
    }

    public void rowUnselect(UnselectEvent event) {
        UIComponent component = event.getComponent();
        DataTable dataTable = findDataTable(component);
        Boolean smooth = (Boolean) dataTable.getAttributes().get("smooth");
        if (smooth) {
            showIndexForm();
        }
    }

    @Override
    public void showIndexForm() {
        copy = null;
        copyIndex = -1;
    }

    @Override
    public void showCreateForm() {
        selectedIndex = -1;
        try {
            copy = elementType.newInstance();
            copyIndex = -1;
        } catch (ReflectiveOperationException e) {
            new FacesUILogger(false).error("创建失败", copy);
        }
    }

    @Override
    public void showEditForm() {
        copy = copyObject(getSelection());
        copyIndex = getSelectedIndex();
    }

    public void apply() {
        if (copy == null)
            return;
        List<T> list = getList();
        if (copyIndex == -1) {
            copyIndex = list.size();
            list.add(copyObject(copy));
        } else {
            list.set(copyIndex, copyObject(copy));
        }
        selectedIndex = copyIndex;
    }

    public void cancel() {
        copyIndex = -1;
        copy = null;
    }

    public static <T> ListMBean<T> fromEL(Object root, String property, Class<T> elementType) {
        return new ELListMBean<>(elementType, root, property);
    }

}
