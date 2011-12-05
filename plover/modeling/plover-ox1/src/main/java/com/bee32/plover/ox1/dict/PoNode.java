package com.bee32.plover.ox1.dict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.free.PrefetchedIterator;
import javax.free.TreeNode;

public class PoNode<T>
        implements TreeNode<PoNode<T>> {

    PoNode<T> parent;
    List<PoNode<T>> children = new ArrayList<PoNode<T>>();
    Object key;
    T data;
    Object userData;
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
        this.parent = parent;
    }

    @Override
    public List<PoNode<T>> getChildren() {
        return children;
    }

    public void addChild(PoNode<T> child) {
        children.add(child);
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

    public Object getUserData() {
        return userData;
    }

    public void setUserData(Object userData) {
        this.userData = userData;
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

}
