package com.bee32.plover.arch.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SelectionHolder
        implements ISelectionHolder, IOpenedObjectHolder, Serializable {

    private static final long serialVersionUID = 1L;

    List<?> selections = new ArrayList<Object>();
    List<?> openedObjects = new ArrayList<Object>();

    List<ISelectionChangeListener> selectionChangeListeners = new ArrayList<>(0);
    List<IObjectOpenListener> objectOpenListeners = new ArrayList<>(0);

    @Override
    public final boolean isSelected() {
        return !getSelection().isEmpty();
    }

    @Override
    public List<?> getSelection() {
        return selections;
    }

    @Override
    public void setSelection(List<?> selections) {
        if (selections == null)
            selections = new ArrayList<Object>();
        if (!this.selections.equals(selections)) {
            this.selections = selections;
            for (ISelectionChangeListener listener : selectionChangeListeners) {
                listener.selectionChanged(selections);
            }
        }
    }

    @Override
    public final Object getSingleSelection() {
        List<?> selection = getSelection();
        if (selection.isEmpty())
            return null;
        else
            return selection.get(0);
    }

    @Override
    public final void setSingleSelection(Object singleSelection) {
        List<Object> list = new ArrayList<Object>();
        if (singleSelection != null)
            list.add(singleSelection);
        setSelection(list);
    }

    @Override
    public final Object[] getSelectionArray() {
        Object[] array = getSelection().toArray();
        return array;
    }

    @Override
    public final void setSelectionArray(Object... selectionArray) {
        List<Object> list = new ArrayList<Object>(selectionArray.length);
        for (Object item : selectionArray)
            list.add(item);
        setSelection(list);
    }

    @Override
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

    @Override
    public final void addSelectionChangeListener(ISelectionChangeListener listener) {
        if (listener == null)
            throw new NullPointerException("listener");
        selectionChangeListeners.add(listener);
    }

    @Override
    public final void removeSelectionChangeListener(ISelectionChangeListener listener) {
        selectionChangeListeners.remove(listener);
    }

    @Override
    public final boolean isOpened() {
        return !getOpenedObjects().isEmpty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> getOpenedObjects() {
        return ((List<T>) openedObjects);
    }

    @Override
    public void setOpenedObjects(List<?> openedObjects) {
        if (openedObjects == null)
            openedObjects = Collections.emptyList();
        if (!this.openedObjects.equals(openedObjects)) {
            this.openedObjects = openedObjects;
            for (IObjectOpenListener listener : objectOpenListeners) {
                listener.objectOpened(openedObjects);
            }
        }
    }

    @Override
    public final <T> T getOpenedObject() {
        List<?> objects = getOpenedObjects();
        if (objects.isEmpty())
            return null;
        T first = (T) objects.get(0);
        return first;
    }

    @Override
    public final void setOpenedObject(Object openedObject) {
        List<?> nonNulls = listOfNonNulls(openedObject);
        setOpenedObjects(nonNulls);
    }

    @Override
    public final void addObjectOpenListener(IObjectOpenListener listener) {
        if (listener == null)
            throw new NullPointerException("listener");
        objectOpenListeners.add(listener);
    }

    @Override
    public final void removeObjectOpenListener(IObjectOpenListener listener) {
        objectOpenListeners.remove(listener);
    }

    // Utils
    @SafeVarargs
    protected static <T> List<T> listOf(T... selection) {
        return Arrays.asList(selection);
    }

    @SafeVarargs
    protected static <T> List<T> listOfNonNulls(T... selection) {
        List<T> list = new ArrayList<T>(selection.length);
        for (T item : selection)
            if (item != null)
                list.add(item);
        return Collections.unmodifiableList(list);
    }

}