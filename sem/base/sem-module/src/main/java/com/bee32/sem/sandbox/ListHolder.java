package com.bee32.sem.sandbox;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.bee32.plover.faces.utils.SelectableList;

/**
 * @see SelectableList
 */
@Deprecated
public class ListHolder<T>
        implements Selectable<T>, Serializable {

    private static final long serialVersionUID = 1L;

    static final boolean DEFAULT_AUTO_INDEX = false;

    boolean autoIndex = DEFAULT_AUTO_INDEX;

    final List<T> list;
    T selection;
    int selectionIndex;

    public ListHolder(List<T> list) {
        if (list == null)
            throw new NullPointerException("list");
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    @Override
    public T getSelection() {
        return selection;
    }

    @Override
    public void setSelection(T selection) {
        this.selection = selection;

        if (autoIndex) {
            int index = list.indexOf(selection);
            selectionIndex = index;
        }
    }

    @Override
    public boolean isSelected() {
        return selection != null;
    }

    @Override
    public void deselect() {
        selection = null;
        selectionIndex = -1;
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public boolean contains(Object o) {
        return list.contains(o);
    }

    public Iterator<T> iterator() {
        return list.iterator();
    }

    public Object[] toArray() {
        return list.toArray();
    }

    public <E> E[] toArray(E[] a) {
        return list.toArray(a);
    }

    public boolean add(T e) {
        return list.add(e);
    }

    public boolean remove(Object o) {
        return list.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    public boolean addAll(Collection<? extends T> c) {
        return list.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        return list.addAll(index, c);
    }

    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    public void clear() {
        list.clear();
    }

    public T get(int index) {
        return list.get(index);
    }

    public T set(int index, T element) {
        return list.set(index, element);
    }

    public void add(int index, T element) {
        list.add(index, element);
    }

    public T remove(int index) {
        return list.remove(index);
    }

    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    public ListIterator<T> listIterator() {
        return list.listIterator();
    }

    public ListIterator<T> listIterator(int index) {
        return list.listIterator(index);
    }

    public List<T> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

}
