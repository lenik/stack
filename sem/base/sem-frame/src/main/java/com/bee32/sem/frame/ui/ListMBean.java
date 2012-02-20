package com.bee32.sem.frame.ui;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;
import javax.faces.component.UIComponent;
import javax.free.CreateException;

import org.apache.commons.collections15.Factory;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.util.dto.BaseDto;
import com.bee32.plover.faces.utils.FacesUILogger;
import com.bee32.plover.faces.utils.SelectableList;

@NotThreadSafe
public abstract class ListMBean<T>
        extends CollectionMBean<T>
        implements IListMBean<T> {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ListMBean.class);

    int selectedIndex = -1;
    int openedIndex = -1;
    boolean editing;

    public ListMBean(Class<T> elementType, Object context) {
        super(elementType, context);
    }

    public ListMBean(Factory<T> factory, Object context) {
        super(factory, context);
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
            if (selectedIndex != openedIndex) {
                openedIndex = -1;
                openedObject = null;
            }
    }

    @Override
    public void removeSelection() {
        if (selectedIndex != -1) {
            List<T> list = getList();
            if (selectedIndex < list.size())
                list.remove(selectedIndex);
            if (openedIndex == selectedIndex) {
                openedIndex = -1;
                openedObject = null;
            }
            selectedIndex = -1;
        }
    }

    public boolean isSelectionEditable() {
        T selection = getSelection();
        if (selection == null)
            return false;
        if (selection instanceof BaseDto<?>) {
            BaseDto<?> dto = (BaseDto<?>) selection;
            if (dto.isLocked())
                return false;
        }
        return true;
    }

    public boolean isSelectionRemovable() {
        T selection = getSelection();
        if (selection == null)
            return false;
        if (selection instanceof BaseDto<?>) {
            BaseDto<?> dto = (BaseDto<?>) selection;
            if (dto.isLocked())
                return false;
        }
        return true;
    }

    @Override
    public int getOpenedIndex() {
        return openedIndex;
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
        openedObject = null;
        openedIndex = -1;
        editing = false;
    }

    @Override
    public void showCreateForm() {
        selectedIndex = -1;
        try {
            openedObject = createElement();
            openedIndex = -1;
        } catch (CreateException e) {
            new FacesUILogger(false).error("创建失败", openedObject);
        }
        editing = true;
    }

    @Override
    public void showEditForm() {
        openedObject = copyObject(getSelection());
        openedIndex = getSelectedIndex();
        editing = true;
    }

    @Override
    public boolean isEditing() {
        return editing;
    }

    public void apply() {
        if (openedObject == null)
            return;
        List<T> list = getList();
        if (openedIndex == -1) {
            openedIndex = list.size();
            list.add(copyObject(openedObject));
        } else {
            list.set(openedIndex, copyObject(openedObject));
        }
        selectedIndex = openedIndex;
    }

    public void cancel() {
        openedIndex = -1;
        openedObject = null;
    }

    public void setAddition(T picked) {
        if (picked == null)
            return; // maybe nullable...?
        else
            getList().add(picked);
    }

    public static <T> ListMBean<T> fromEL(Object root, String property, Factory<T> elementFactory) {
        return new ELListMBean<T>(elementFactory, root, property);
    }

    public static <T> ListMBean<T> fromEL(Object root, String property, Class<T> elementType) {
        return new ELListMBean<T>(elementType, root, property);
    }

}
