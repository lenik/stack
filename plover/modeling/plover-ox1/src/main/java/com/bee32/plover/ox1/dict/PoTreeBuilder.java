package com.bee32.plover.ox1.dict;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.free.IPreorder;
import javax.free.ITransformer;

public class PoTreeBuilder<K> {

    Map<K, PoNode> nodes;
    ITransformer<Object, K> mapper;
    IPreorder<K> preorder;

    public PoTreeBuilder() {
        nodes = new HashMap<K, PoNode>();
    }

    public void convert(Collection<?> collection) {
        for (Object obj : collection) {
            K key = (K) mapper.transform(obj);
            K preceding = preorder.getPreceding(key);
        }
    }

    public PoNode getNode(Object obj) {
        K key = mapper.transform(obj);
        PoNode node = nodes.get(key);
        if (node == null) {
            node = new PoNode();
            node.setVirtual(true);
            nodes.put(key, node);
        }
        return node;
    }

}
