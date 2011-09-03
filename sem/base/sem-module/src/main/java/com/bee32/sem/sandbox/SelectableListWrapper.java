package com.bee32.sem.sandbox;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SelectableListWrapper<T>
        implements SelectableList<T>, Serializable {

    private static final long serialVersionUID = 1L;

    static final boolean DEFAULT_AUTO_INDEX = false;

    boolean autoIndex = DEFAULT_AUTO_INDEX;

    final List<T> list;
    T selection;
    int selectionIndex;

    public SelectableListWrapper(List<T> list) {
        if (list == null)
            throw new NullPointerException("list");
        this.list = list;
    }

    @Override
    public T getSelection() {
        return selection;
    }

    @Override
    public void setSelection(T selection) {
        this.selection = selection;

        if (autoIndex) {
            int index = indexOf(selection);
            selectionIndex = index;
        }
    }

    /**
     * Only meaningful if the list does not contain <code>null</code> element.
     */
    @Override
    public boolean isSelected() {
        return selection != null;
    }

    @Override
    public void deselect() {
        selection = null;
    }

    @Override
    public int getSelectionIndex() {
        return selectionIndex;
    }

    @Override
    public void setSelectionIndex(int i) {
        this.selectionIndex = i;
        if (selectionIndex >= 0 && selectionIndex < size()) {
            selection = get(i);
        }
    }

    @Override
    public boolean equals(Object o) {
        return list.equals(o);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <_T> _T[] toArray(_T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(T e) {
        return list.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return list.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public T set(int index, T element) {
        return list.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        list.add(index, element);
    }

    @Override
    public T remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

}
