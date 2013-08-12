package com.bee32.sem.frame.ui;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;
import javax.faces.component.UIComponent;
import javax.free.CreateException;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.util.dto.BaseDto;
import com.bee32.plover.faces.utils.FacesUILogger;
import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.orm.util.EntityDto;

@NotThreadSafe
public abstract class ListMBean<T>
        extends CollectionMBean<T>
        implements IListMBean<T> {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ListMBean.class);

    int[] selectedIndexes = {};
    int openedIndex = -1;
    boolean editing;

    int[] EMPTY_SELECTION = {};

    public ListMBean(Class<T> elementType, Object context) {
        super(elementType, context);
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
    public synchronized T getLastSelection() {
        List<T> list = getList();
        if (selectedIndexes.length == 0)
            return null;

        int firstIndex = selectedIndexes[0];
        if (firstIndex >= list.size())
            return null;

        return list.get(firstIndex);
    }

    @Override
    public synchronized void setLastSelection(T selection) {
        List<T> list = getList();
        int index = list.indexOf(selection);
        setSelectedIndexes(index);
    }

    public int getLastIndex() {
        int count = selectedIndexes.length;
        if (count == 0)
            return -1;
        else
            return selectedIndexes[count - 1];
    }

    @Override
    public synchronized T[] getSelection() {
        int length = selectedIndexes.length;
        T[] selection = (T[]) Array.newInstance(elementType, length);
        List<T> list = getList();
        int off = 0;
        for (int index : selectedIndexes) {
            T element = list.get(index);
            Array.set(selection, off++, element);
        }
        return selection;
    }

    @Override
    public synchronized void setSelection(T[] selection) {
        int length = selection.length;
        int[] indexes = new int[length];
        List<T> list = getList();
        int off = 0;
        for (T element : selection) {
            int index = list.indexOf(element);
            if (index == -1) {
                logger.warn("Invalid element in the selection: " + element);
                continue;
            }
            indexes[off++] = index;
        }
        if (off != length)
            indexes = Arrays.copyOf(indexes, off);
        this.selectedIndexes = indexes;
    }

    @Override
    public int[] getSelectedIndexes() {
        return selectedIndexes;
    }

    @Override
    public void setSelectedIndexes(int... selectedIndexes) {
        this.selectedIndexes = selectedIndexes;
        int lastIndex = getLastIndex();
        if (copyMode)
            if (lastIndex != openedIndex) {
                openedIndex = -1;
                openedObject = null;
            }
    }

    @Override
    public void removeSelection() {
        Arrays.sort(selectedIndexes);
        int count = selectedIndexes.length;
        boolean containsOpenedObject = false;

        for (int i = count - 1; i >= 0; i--) {
            int index = selectedIndexes[i];

            List<T> list = getList();
            if (index < list.size())
                list.remove(index);

            if (openedIndex == index)
                containsOpenedObject = true;
        }

        selectedIndexes = EMPTY_SELECTION;
        if (containsOpenedObject) {
            openedIndex = -1;
            openedObject = null;
        }
    }

    public boolean isSelectionEditable() {
        T[] selection = getSelection();
        for (T obj : selection)
            if (obj instanceof BaseDto<?>) {
                BaseDto<?> dto = (BaseDto<?>) obj;
                if (dto.isLocked())
                    return false;
            }
        return true;
    }

    public boolean isSelectionRemovable() {
        T[] selection = getSelection();
        for (T obj : selection)
            if (obj instanceof BaseDto<?>) {
                BaseDto<?> dto = (BaseDto<?>) obj;
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
        selectedIndexes = EMPTY_SELECTION;
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
        T selection = getLastSelection();
        openedObject = copyObject(selection);
        openedIndex = getLastIndex();
        editing = true;
    }

    @Override
    public boolean isEditing() {
        return editing;
    }

    public void dup() {
        if (openedObject == null)
            return;
        List<T> list = getList();
        T dup = _copyObject(openedObject);
        if (dup instanceof EntityDto<?, ?>) {
            EntityDto<?, ?> dto = (EntityDto<?, ?>) dup;
            dto.clearId();
            dto.setEntityFlags(0);
        }
        list.add(dup);
    }

    public void apply() {
        if (openedObject == null)
            return;

        List<T> list = getList();
        if (openedIndex == -1) { // create
            openedIndex = list.size();
            list.add(copyObject(openedObject));
        } else { // edit
            list.set(openedIndex, copyObject(openedObject));
        }

        selectedIndexes = new int[] { openedIndex };
    }

    public void cancel() {
        openedIndex = -1;
        openedObject = null;
    }

    public void setAddition(T picked) {
        if (picked == null)
            return; // maybe nullable...?
        List<T> list = getList();
        list.add(picked);
    }

    public static <T> ListMBean<T> fromEL(Object root, String property, Class<T> elementType) {
        return new ELListMBean<T>(elementType, root, property);
    }

}
