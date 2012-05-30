package com.bee32.plover.ox1.dict;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.free.AbstractNonNullComparator;
import javax.free.ComparableComparator;
import javax.free.IFormatter;
import javax.free.INegotiation;
import javax.free.IllegalUsageException;
import javax.free.NegotiationException;
import javax.free.PrefetchedIterator;
import javax.free.TreeNode;

public class PoNode<T>
        implements TreeNode<PoNode<T>> {

    PoNode<T> parent;
    List<PoNode<T>> children = new ArrayList<PoNode<T>>();
    Object key;
    T data;
    Map<String, Object> attributeMap;

    public PoNode() {
    }

    public synchronized void attach(PoNode<T> parent) {
        detach();

        this.parent = parent;
        if (parent != null)
            parent.addChild(this);
    }

    public synchronized void detach() {
        if (parent != null)
            parent.removeChild(this);
        parent = null;
    }

    public boolean isVirtual() {
        return data == null;
    }

    public PoNode<T> getParent() {
        return parent;
    }

    public void setParent(PoNode<T> parent) {
        if (parent == this)
            throw new IllegalUsageException("Bad parent to form dead-loop: " + parent);
        this.parent = parent;
    }

    @Override
    public List<PoNode<T>> getChildren() {
        return children;
    }

    static class PoNodeComparator extends AbstractNonNullComparator<PoNode<?>> {
        static final Comparator<Object> cc = ComparableComparator.getRawInstance();
        @Override
        public int compareNonNull(PoNode<?> o1, PoNode<?> o2) {
            Object k1 = o1.getKey();
            Object k2 = o2.getKey();
            int cmp = cc.compare(k1, k2);
            if (cmp == 0)
                cmp = compareIdentity(o1, o2);
            return cmp;
        }
        static final PoNodeComparator INSTANCE = new PoNodeComparator();
    }

    public void addChild(PoNode<T> child) {
        // pos = -i - 1, -i = pos + 1, i = -pos - 1
        int pos = Collections.binarySearch(children, child, PoNodeComparator.INSTANCE);
        int insert = -pos - 1;
        children.add(insert, child);
    }

    public void removeChild(PoNode<T> child) {
        children.remove(child);
    }

    class ChainIterator
            extends PrefetchedIterator<PoNode<T>> {

        PoNode<T> node = PoNode.this;

        @Override
        protected PoNode<T> fetch() {
            if (node != null) {
                PoNode<T> fetched = node;
                node = node.getParent();
                return fetched;
            }
            return end();
        }

    }

    public Iterable<PoNode<T>> chain() {
        return new Iterable<PoNode<T>>() {

            @Override
            public ChainIterator iterator() {
                return new ChainIterator();
            }

        };
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public synchronized Map<String, Object> getAttributeMap() {
        if (attributeMap == null)
            attributeMap = new HashMap<String, Object>();
        return attributeMap;
    }

    public Object getAttribute(String key) {
        return getAttributeMap().get(key);
    }

    public void setAttribute(String key, Object value) {
        getAttributeMap().put(key, value);
    }

    @Override
    public String toString() {
        return "(" + key + ": " + data + ")";
    }

    public static class KeyFormatter
            implements IFormatter<PoNode<?>> {

        @Override
        public String format(PoNode<?> node, INegotiation n)
                throws NegotiationException {
            return format(node);
        }

        @Override
        public String format(PoNode<?> node) {
            Object key = node.getKey();
            return String.valueOf(key);
        }

        public static final KeyFormatter INSTANCE = new KeyFormatter();
    }

}
