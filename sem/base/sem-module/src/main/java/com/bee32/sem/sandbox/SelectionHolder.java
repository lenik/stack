package com.bee32.sem.sandbox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.bee32.plover.arch.util.ISelection;

public class SelectionHolder
        implements ISelection, Serializable {

    private static final long serialVersionUID = 1L;

    List<?> selection = new ArrayList<Object>();

    @Override
    public List<?> getSelection() {
        return selection;
    }

    public final List<?> getSelection(Class<?>... interfaces) {
        List<?> selection = getSelection();
        if (interfaces.length == 0)
            return selection;
        List<Object> interestings = new ArrayList<Object>();
        for (Object item : selection) {
            boolean interesting = true;
            if (item == null)
                continue;
            for (Class<?> iface : interfaces)
                if (!iface.isInstance(item)) {
                    interesting = false;
                    break;
                }
            if (interesting)
                interestings.add(item);
        }
        return interestings;
    }

    public void setSelection(List<?> selection) {
        if (selection == null)
            selection = new ArrayList<Object>();
        this.selection = selection;
    }

    public final Object getSingleSelection() {
        List<?> selection = getSelection();
        if (selection.isEmpty())
            return null;
        else
            return selection.get(0);
    }

    public final void setSingleSelection(Object singleSelection) {
        System.out.println("SS: " + singleSelection);
        List<Object> list = new ArrayList<Object>();
        if (singleSelection != null)
            list.add(singleSelection);
        setSelection(list);
    }

    public final Object[] getSelectionArray() {
        Object[] array = selection.toArray();
        return array;
    }

    public final void setSelectionArray(Object... selectionArray) {
        List<Object> list = new ArrayList<Object>(selectionArray.length);
        for (Object item : selectionArray)
            list.add(item);
        setSelection(list);
    }

    public final boolean isSelected() {
        return !getSelection().isEmpty();
    }

}
