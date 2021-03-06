package com.bee32.plover.faces.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class SelectableList<T>
        extends ListDataModel<T>
        implements List<T>, SelectableDataModel<T> {

    List<T> list;

    SelectableList(List<T> list) {
        super(list);
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    @Override
    public String getRowKey(T object) {
        int index = list.indexOf(object);
        return String.valueOf(index);
    }

    @Override
    public T getRowData(String rowKey) {
        int index = Integer.parseInt(rowKey);
        if (index == -1)
            return null;
        if (index >= list.size())
            return null; // assert false.
        return list.get(index);
    }

    public static <T> SelectableList<T> decorate(List<T> list) {
        return new SelectableList<>(list);
    }

    /** ⇱ Implementation Of {@link List}. */
    ;

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

    @Override
    public String toString() {
        return list.toString();
    }

}
