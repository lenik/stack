package com.bee32.sem.misc;

import java.util.List;

import com.bee32.plover.arch.util.IObjectOpenListener;
import com.bee32.plover.arch.util.IPriority;
import com.bee32.plover.arch.util.ISelectionChangeListener;
import com.bee32.plover.orm.util.DataViewBean;

public abstract class SevbFriend
        extends DataViewBean
        implements IPriority, ISelectionChangeListener, IObjectOpenListener {

    private static final long serialVersionUID = 1L;

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void selectionChanged(List<?> selections) {
        if (selections.isEmpty())
            unselect();
        else {
            Object first = selections.get(0);
            select(first);
        }
    }

    protected abstract void select(Object mainEntry);

    protected void unselect() {
        setSelection(null);
    }

    @Override
    public void objectOpened(List<?> openedObjects) {
        if (openedObjects.isEmpty())
            close();
        else {
            Object first = openedObjects.get(0);
            open(first);
        }
    }

    protected abstract void open(Object mainOpenedObject);

    protected void close() {
        setOpenedObject(null);
    }

    public boolean preUpdate(UnmarshalMap uMap) {
        return true;
    }

    public void postUpdate(UnmarshalMap uMap) {
    }

    public boolean preDelete(UnmarshalMap uMap) {
        return true;
    }

    public void postDelete(UnmarshalMap uMap) {
    }

    public abstract void saveOpenedObject(int saveFlags);

    public abstract void deleteSelection(int deleteFlags);

}
