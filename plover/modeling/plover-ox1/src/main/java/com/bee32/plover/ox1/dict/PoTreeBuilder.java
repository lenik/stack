package com.bee32.plover.ox1.dict;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.free.IPreorder;
import javax.free.IPrintOut;
import javax.free.NotImplementedException;

import com.bee32.plover.util.PrettyPrintStream;

public class PoTreeBuilder<T, K> {

    private Set<PoNode> roots;
    private Map<K, PoNode> nodes;
    private final IKeyMapper<T, K> mapper;
    private final IPreorder<K> preorder;

    public PoTreeBuilder(IKeyMapper<T, K> mapper, IPreorder<K> preorder) {
        this.roots = new HashSet<PoNode>();
        this.nodes = new HashMap<K, PoNode>();
        this.mapper = mapper;
        this.preorder = preorder;
    }

    public void collect(Collection<? extends T> collection) {
        for (T obj : collection)
            collect(obj);
    }

    public synchronized PoNode collect(T obj) {
        K key = mapper.getKey(obj);
        PoNode node = nodes.get(key);
        if (node == null) {
            K parentKey = preorder.getPreceding(key);
            PoNode parentNode = getOrCreateVirtualNode(parentKey);
            node = new PoNode();
            node.attach(parentNode);
            nodes.put(key, node);
        }
        // else if (node.getData() != null) throw new IllegalStateException("node duplicated...");
        node.setKey(key);
        node.setData(obj);
        return node;
    }

    public synchronized Set<K> reduce() {
        Set<K> danglingKeys = new HashSet<K>();
        for (PoNode root : roots) {
            _reduce(root, danglingKeys);
        }
        return danglingKeys;
    }

    private Collection<PoNode> _reduce(PoNode node, Set<K> danglingKeys) {
        List<PoNode> children = node.getChildren();
        int size = children.size();

        for (int i = 0; i < size; i++) {
            PoNode child = children.get(i);
            Collection<PoNode> reduced = _reduce(child, danglingKeys);
            if (reduced != null) {
                // Remove the virtual node.
                PoNode danglingNode = children.remove(i);
                size--;
                i--;
                // assert danglingNode.isVirtual();
                K danglingKey = (K) danglingNode.getKey();
                danglingKeys.add(danglingKey);

                // Replace with the reduced set.
                for (PoNode replacement : reduced) {
                    replacement.setParent(node);
                    children.add(++i, replacement);
                }
                size += reduced.size();

                danglingNode.children.clear();
                nodes.remove(danglingKey);
            }
        }

        if (node.isVirtual())
            return node.getChildren();
        else
            return null;
    }

    public void expand() {
        throw new NotImplementedException();
    }

    public PoNode getNode(K key) {
        return nodes.get(key);
    }

    protected synchronized PoNode getOrCreateVirtualNode(K key) {
        if (key == null)
            return null;

        PoNode node = nodes.get(key);
        if (node == null) {
            node = new PoNode();
            node.setKey(key);
            nodes.put(key, node);

            K parentKey = preorder.getPreceding(key);
            PoNode parentNode = getOrCreateVirtualNode(parentKey);
            if (parentNode == null)
                roots.add(node);
            else
                node.attach(parentNode);
        }
        return node;
    }

    public String dump() {
        PrettyPrintStream buf = new PrettyPrintStream();
        dump(buf);
        return buf.toString();
    }

    public void dump(IPrintOut out) {
        for (PoNode root : roots) {
            out.println("Root " + root.getKey());
            _dump(out, root, "");
        }
    }

    void _dump(IPrintOut out, PoNode node, String prefix) {
        List<PoNode> children = node.getChildren();
        for (int i = 0; i < children.size(); i++) {
            PoNode child = children.get(i);
            boolean end = i == children.size() - 1;

            out.println(prefix + (end ? " `- " : " |- ") + formatEntry(child));

            _dump(out, child, prefix + (end ? "    " : " |  "));
        }
    }

    protected String formatEntry(PoNode node) {
        K key = (K) node.getKey();
        // Object obj= node.getData();
        return String.valueOf(key);
    }

}
