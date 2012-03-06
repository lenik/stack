package com.bee32.plover.orm.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CloneUtils {

    public static <E extends Entity<?>> E clone(E source) {
        if (source == null)
            return null;
        else
            return (E) source.clone();
    }

    public static <E extends Entity<?>> Set<E> cloneSet(Set<E> list) {
        Set<E> newSet = new HashSet<E>();
        for (E element : list) {
            E copy = (E) element.clone();
            newSet.add(copy);
        }
        return newSet;
    }

    public static <E extends Entity<?>> List<E> cloneList(List<E> list) {
        List<E> newList = new ArrayList<E>();
        for (E element : list) {
            E copy = (E) element.clone();
            newList.add(copy);
        }
        return newList;
    }

}
