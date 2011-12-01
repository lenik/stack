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

    private Set<PoNode<T>> roots;
    private Map<K, PoNode<T>> nodes;
    private final IKeyMapper<T, K> mapper;
    private final IPreorder<K> preorder;

    public PoTreeBuilder(IKeyMapper<T, K> mapper, IPreorder<K> preorder) {
        this.roots = new HashSet<PoNode<T>>();
        this.nodes = new HashMap<K, PoNode<T>>();
        this.mapper = mapper;
        this.preorder = preorder;
    }

    public void learn(Collection<? extends T> collection) {
        for (T obj : collection)
            learn(obj);
    }

    public synchronized PoNode<T> learn(T obj) {
        K key = mapper.getKey(obj);
        PoNode<T> node = nodes.get(key);
        if (node == null) {
            K parentKey = preorder.getPreceding(key);
            PoNode<T> parentNode = getOrCreateVirtualNode(parentKey);
            node = new PoNode<T>();
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
        for (PoNode<T> root : roots) {
            _reduce(root, danglingKeys);
        }
        return danglingKeys;
    }

    private Collection<PoNode<T>> _reduce(PoNode<T> node, Set<K> danglingKeys) {
        List<PoNode<T>> children = node.getChildren();
        int size = children.size();

        for (int i = 0; i < size; i++) {
            PoNode<T> child = children.get(i);
            Collection<PoNode<T>> reduced = _reduce(child, danglingKeys);
            if (reduced != null) {
                // Remove the virtual node.
                PoNode<T> danglingNode = children.remove(i);
                size--;
                i--;
                // assert danglingNode.isVirtual();
                K danglingKey = (K) danglingNode.getKey();
                danglingKeys.add(danglingKey);

                // Replace with the reduced set.
                for (PoNode<T> replacement : reduced) {
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

    public Collection<PoNode<T>> getRoots() {
        return roots;
    }

    public synchronized PoNode<T> getRoot() {
        if (roots.isEmpty())
            throw new IllegalStateException("No root node in the tree.");
        if (roots.size() > 1)
            throw new IllegalStateException("Too many root nodes in the tree!");
        return roots.iterator().next();
    }

    public PoNode<T> getNode(K key) {
        return nodes.get(key);
    }

    protected synchronized PoNode<T> getOrCreateVirtualNode(K key) {
        if (key == null)
            return null;

        PoNode<T> node = nodes.get(key);
        if (node == null) {
            node = new PoNode<T>();
            node.setKey(key);
            nodes.put(key, node);

            K parentKey = preorder.getPreceding(key);
            PoNode<T> parentNode = getOrCreateVirtualNode(parentKey);
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
        for (PoNode<T> root : roots) {
            out.println("Root " + root.getKey());
            _dump(out, root, "");
        }
    }

    void _dump(IPrintOut out, PoNode<T> node, String prefix) {
        List<PoNode<T>> children = node.getChildren();
        for (int i = 0; i < children.size(); i++) {
            PoNode<T> child = children.get(i);
            boolean end = i == children.size() - 1;

            out.println(prefix + (end ? " `- " : " |- ") + formatEntry(child));

            _dump(out, child, prefix + (end ? "    " : " |  "));
        }
    }

    protected String formatEntry(PoNode<T> node) {
        K key = (K) node.getKey();
        // Object obj= node.getData();
        return String.valueOf(key);
    }

}
