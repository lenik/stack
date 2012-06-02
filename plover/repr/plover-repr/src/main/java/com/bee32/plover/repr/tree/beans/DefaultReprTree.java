package com.bee32.plover.repr.tree.beans;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.bee32.plover.repr.tree.IReprTree;
import com.bee32.plover.repr.tree.TreeIterator;

public class DefaultReprTree
        implements IReprTree {

    Map<String, Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    @Override
    public Iterator<Entry<String, ?>> entries() {
        // Iterator<Entry<String, Object>> entrySet = map.entrySet().iterator();
        throw new UnsupportedOperationException();
    }

    @Override
    public TreeIterator<?> walk() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object get(String path) {
        return null;
    }

}
