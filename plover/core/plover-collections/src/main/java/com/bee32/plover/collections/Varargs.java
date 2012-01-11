package com.bee32.plover.collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Varargs {

    @SafeVarargs
    public static <T> List<T> toList(T... array) {
        List<T> list = new ArrayList<T>(array.length);
        for (T element : array)
            list.add(element);
        return list;
    }

    @SafeVarargs
    public static <T> List<T> toLinkedList(T... array) {
        List<T> list = new LinkedList<T>();
        for (T element : array)
            list.add(element);
        return list;
    }

    @SafeVarargs
    public static <T> Set<T> toSet(T... array) {
        Set<T> set = new HashSet<T>(array.length);
        for (T element : array)
            set.add(element);
        return set;
    }

    @SafeVarargs
    public static <T> Set<T> toLinkedSet(T... array) {
        Set<T> set = new LinkedHashSet<T>(array.length);
        for (T element : array)
            set.add(element);
        return set;
    }

    @SafeVarargs
    public static <T> SortedSet<T> toSortedSet(T... array) {
        return toSortedSet(null, array);
    }

    @SafeVarargs
    public static <T> SortedSet<T> toSortedSet(Comparator<? super T> comparator, T... array) {
        SortedSet<T> set = new TreeSet<T>(comparator);
        for (T element : array)
            set.add(element);
        return set;
    }

}
