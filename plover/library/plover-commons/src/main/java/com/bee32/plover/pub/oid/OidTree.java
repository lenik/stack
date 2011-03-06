package com.bee32.plover.pub.oid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.bee32.plover.arch.naming.NamedNode;

public class OidTree<T>
        extends NamedNode
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private Class<T> dataType;

    private T data;
    private Map<Integer, OidTree<T>> children;

    public OidTree(Class<T> dataType) {
        super(dataType, null);
        this.dataType = dataType;
    }

    public OidTree(String name, Class<T> dataType) {
        super(name, dataType, null);
        this.dataType = dataType;
    }

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
            tree = new OidTree<T>(dataType);
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
    public Object getChild(String childName) {
        String[] split = childName.split("/");
        int[] vector = new int[split.length];
        for (int i = 0; i < vector.length; i++)
            vector[i] = Integer.parseInt(split[i]);

        OidTree<T> childTree = get(vector);

        return childTree.data;
    }

    @Override
    public String getChildName(Object obj) {
        Map<T, String> reverseMap = getReverseMap();
        return reverseMap.get(obj);
    }

    public Map<T, String> getReverseMap() {
        if (children == null || children.isEmpty())
            return Collections.emptyMap();

        Map<T, String> map = new IdentityHashMap<T, String>();
        dumpReverseMap(map, new StringBuffer());
        return map;
    }

    void dumpReverseMap(Map<T, String> map, StringBuffer prefix) {
        if (data != null)
            map.put(data, prefix.toString());

        if (children == null)
            return;

        if (prefix.length() != 0)
            prefix.append('/');

        int len = prefix.length();

        for (Entry<Integer, OidTree<T>> entry : children.entrySet()) {
            int ord = entry.getKey();
            OidTree<T> childTree = entry.getValue();

            prefix.setLength(len);
            prefix.append(ord);

            childTree.dumpReverseMap(map, prefix);
        }
    }

    @Override
    public Collection<String> getChildNames() {
        if (children == null || children.isEmpty())
            return Collections.emptyList();

        List<String> names = new ArrayList<String>();
        dumpChildNames(names, new StringBuffer());
        return names;
    }

    void dumpChildNames(List<String> list, StringBuffer prefix) {
        if (data != null)
            list.add(prefix.toString());

        if (children == null)
            return;

        if (prefix.length() != 0)
            prefix.append('/');

        int len = prefix.length();

        for (Entry<Integer, OidTree<T>> entry : children.entrySet()) {
            int ord = entry.getKey();
            OidTree<T> childTree = entry.getValue();

            prefix.setLength(len);
            prefix.append(ord);

            childTree.dumpChildNames(list, prefix);
        }
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
