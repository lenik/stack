package com.bee32.plover.web.faces.controls2;

import java.util.ArrayList;
import java.util.List;

import com.bee32.plover.arch.operation.Operation;
import com.bee32.plover.web.faces.utils.FacesContextSupport;

public abstract class AbstractChooseDialogListener
        extends FacesContextSupport
        implements IDialogCallback {

    List<?> selection = new ArrayList<Object>();
    boolean cancelled;

    @Operation
    @Override
    public abstract void submit();

    @Operation
    @Override
    public void cancel() {
        cancelled = true;
    }

    public List<?> getSelection() {
        return selection;
    }

    public List<?> getSelection(Class<?>... interfaces) {
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

    public Object getSingleSelection() {
        List<?> selection = getSelection();
        if (selection.isEmpty())
            return null;
        else
            return selection.get(0);
    }

    public void setSingleSelection(Object singleSelection) {
        List<Object> list = new ArrayList<Object>();
        if (singleSelection != null)
            list.add(singleSelection);
        setSelection(list);
    }

    public Object[] getSelectionArray() {
        return selection.toArray();
    }

    public void setSelectionArray(Object... selectionArray) {
        List<Object> list = new ArrayList<Object>(selectionArray.length);
        for (Object item : selectionArray)
            list.add(item);
        setSelection(list);
    }

    public boolean isSelected() {
        return !getSelection().isEmpty();
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
