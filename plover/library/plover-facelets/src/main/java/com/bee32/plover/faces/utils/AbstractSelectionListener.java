package com.bee32.plover.faces.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.bee32.plover.arch.operation.Operation;
import com.bee32.plover.arch.util.ISelection;

public abstract class AbstractSelectionListener
        extends FacesContextUtils
        implements IDialogCallback {

    private static class SelectionWrapper
            implements ISelection, Serializable {

        private static final long serialVersionUID = 1L;

        final Class<? extends ISelection> selectionSourceBeanType;

        SelectionWrapper(Class<? extends ISelection> selectionSourceBeanType) {
            this.selectionSourceBeanType = selectionSourceBeanType;
        }

        @Override
        public List<?> getSelection() {
            ISelection bean = getBean(selectionSourceBeanType);
            return bean.getSelection();
        }

    }

    final ISelection selectionSource;
    boolean cancelled;

    public AbstractSelectionListener(Class<? extends ISelection> selectionSourceBeanType) {
        this(new SelectionWrapper(selectionSourceBeanType));
    }

    public AbstractSelectionListener(ISelection selectionSource) {
        if (selectionSource == null)
            throw new NullPointerException("selectionSource");
        this.selectionSource = selectionSource;
    }

    @Operation
    @Override
    public void selected() {
        List<?> selection = getSelection();
        selected(selection);
    }

    protected abstract void selected(List<?> selection);

    @Operation
    @Override
    public void cancel() {
        cancelled = true;
    }

    List<?> getSelection() {
        return selectionSource.getSelection();
    }

    List<?> getSelection(Class<?>... interfaces) {
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

    Object getSingleSelection() {
        List<?> selection = getSelection();
        if (selection.isEmpty())
            return null;
        else
            return selection.get(0);
    }

    Object[] getSelectionArray() {
        return getSelection().toArray();
    }

    boolean isSelected() {
        return !getSelection().isEmpty();
    }

    protected boolean isCancelled() {
        return cancelled;
    }

    protected void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
