package com.bee32.plover.pub.oid;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class OidTree<T>
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private T data;
    private Map<Integer, OidTree<T>> children;

    public T get() {
        return data;
    }

    public void set(T value) {
        this.data = value;
    }

    public synchronized boolean contains(int ord) {
        if (children == null)
            return false;
        return children.containsKey(ord);
    }

    public synchronized OidTree<T> get(int ord) {
        if (children == null)
            children = new HashMap<Integer, OidTree<T>>();

        OidTree<T> tree = children.get(ord);
        if (tree == null) {
            tree = new OidTree<T>();
            children.put(ord, tree);
        }
        return tree;
    }

    public synchronized void set(int ord, T value) {
        get(ord).set(value);
    }

    public boolean contains(OidVector oid) {
        return contains(oid.vector);
    }

    public boolean contains(int... vector) {
        return _contains(vector, 0, vector.length);
    }

    protected boolean _contains(int[] vector, int start, int end) {
        OidTree<T> node = this;
        while (start < end) {
            int ord = vector[start++];
            if (!node.contains(ord))
                return false;
            node = node.get(ord);
        }
        return true;
    }

    public OidTree<T> get(OidVector oid) {
        return get(oid.vector);
    }

    public OidTree<T> get(int... vector) {
        return _get(vector, 0, vector.length);
    }

    protected OidTree<T> _get(int[] vector, int start, int end) {
        OidTree<T> node = this;
        while (start < end) {
            int ord = vector[start++];
            node = node.get(ord);
        }
        return node;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((children == null) ? 0 : children.hashCode());
        result = prime * result + ((data == null) ? 0 : data.hashCode());
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
        OidTree<?> other = (OidTree<?>) obj;
        if (children == null) {
            if (other.children != null)
                return false;
        } else if (!children.equals(other.children))
            return false;
        if (data == null) {
            if (other.data != null)
                return false;
        } else if (!data.equals(other.data))
            return false;
        return true;
    }

}
