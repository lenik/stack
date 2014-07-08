package com.bee32.zebra.tk.collections.map;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.TreeMap;

import org.apache.commons.collections15.map.AbstractSortedMapDecorator;

public abstract class RangeMap<V, T>
        extends AbstractSortedMapDecorator<V, T>
        implements IRangeMap<V, T>, Serializable {

    private static final long serialVersionUID = 1L;

    protected final Comparator<? super V> comparator;
    protected final TreeMap<V, T> treeMap;

    public RangeMap(Comparator<? super V> comparator) {
        this(comparator, new TreeMap<V, T>());
    }

    public RangeMap(Comparator<? super V> comparator, Map<? extends V, ? extends T> map) {
        this(comparator, new TreeMap<V, T>(map));
    }

    public RangeMap(Comparator<? super V> comparator, TreeMap<V, T> treeMap) {
        super(treeMap);

        if (comparator == null)
            throw new NullPointerException("comparator");
        if (treeMap == null)
            throw new NullPointerException("treeMap");

        this.comparator = comparator;
        this.treeMap = treeMap;
    }

    public void addEntries(Collection<? extends IRangeMapEntry<V, T>> entries) {
        if (entries == null)
            throw new NullPointerException("entries");

        for (IRangeMapEntry<V, T> entry : entries) {

            V boundary = entry.getX();
            T target = entry.getTarget();

            this.put(boundary, target);
        }
    }

    @Override
    public T getNarrowedTarget(V boundaryPoint) {
        return this.get(boundaryPoint);
    }

    @Override
    public final T getTarget(V floatPoint) {
        V boundaryPoint = narrowDown(floatPoint);

        if (boundaryPoint == null)
            return null;

        T target = getNarrowedTarget(boundaryPoint);
        return target;
    }

    // NavigableMap delegates

    @Override
    public java.util.Map.Entry<V, T> lowerEntry(V key) {
        return treeMap.lowerEntry(key);
    }

    @Override
    public V lowerKey(V key) {
        return treeMap.lowerKey(key);
    }

    @Override
    public java.util.Map.Entry<V, T> floorEntry(V key) {
        return treeMap.floorEntry(key);
    }

    @Override
    public V floorKey(V key) {
        return treeMap.floorKey(key);
    }

    @Override
    public java.util.Map.Entry<V, T> ceilingEntry(V key) {
        return treeMap.ceilingEntry(key);
    }

    @Override
    public V ceilingKey(V key) {
        return treeMap.ceilingKey(key);
    }

    @Override
    public java.util.Map.Entry<V, T> higherEntry(V key) {
        return treeMap.higherEntry(key);
    }

    @Override
    public V higherKey(V key) {
        return treeMap.higherKey(key);
    }

    @Override
    public java.util.Map.Entry<V, T> firstEntry() {
        return treeMap.firstEntry();
    }

    @Override
    public java.util.Map.Entry<V, T> lastEntry() {
        return treeMap.lastEntry();
    }

    @Override
    public java.util.Map.Entry<V, T> pollFirstEntry() {
        return treeMap.pollFirstEntry();
    }

    @Override
    public java.util.Map.Entry<V, T> pollLastEntry() {
        return treeMap.pollLastEntry();
    }

    @Override
    public NavigableMap<V, T> descendingMap() {
        return treeMap.descendingMap();
    }

    @Override
    public NavigableSet<V> navigableKeySet() {
        return treeMap.navigableKeySet();
    }

    @Override
    public NavigableSet<V> descendingKeySet() {
        return treeMap.descendingKeySet();
    }

    @Override
    public NavigableMap<V, T> subMap(V fromKey, boolean fromInclusive, V toKey, boolean toInclusive) {
        return treeMap.subMap(fromKey, fromInclusive, toKey, toInclusive);
    }

    @Override
    public NavigableMap<V, T> headMap(V toKey, boolean inclusive) {
        return treeMap.headMap(toKey, inclusive);
    }

    @Override
    public NavigableMap<V, T> tailMap(V fromKey, boolean inclusive) {
        return treeMap.tailMap(fromKey, inclusive);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((comparator == null) ? 0 : comparator.hashCode());
        result = prime * result + ((treeMap == null) ? 0 : treeMap.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        RangeMap<?, ?> other = (RangeMap<?, ?>) obj;

        if (!comparator.equals(other.comparator))
            return false;

        if (!treeMap.equals(other.treeMap))
            return false;

        return true;
    }

    @Override
    public String toString() {
        return treeMap.toString();
    }

}
