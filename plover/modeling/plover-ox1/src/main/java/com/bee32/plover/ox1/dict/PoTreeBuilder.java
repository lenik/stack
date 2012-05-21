package com.bee32.plover.ox1.dict;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.free.IFormatter;
import javax.free.IPreorder;
import javax.free.IPrintOut;
import javax.free.NotImplementedException;

import com.bee32.plover.util.PrettyPrintStream;

public class PoTreeBuilder<T, K> {

    private Set<PoNode<T>> roots;
    private Map<K, PoNode<T>> nodes;
    private final IKeyMapper<T, K> mapper;
    private final IPreorder<K> preorder;
    private IFormatter<PoNode<?>> formatter = PoNode.KeyFormatter.INSTANCE;

    @SuppressWarnings("unchecked")
    private Class<? extends PoNode<? extends T>> nodeClass = (Class<? extends PoNode<? extends T>>) (Object) PoNode.class;

    public PoTreeBuilder(IKeyMapper<T, K> mapper, IPreorder<K> preorder) {
        this.roots = new HashSet<PoNode<T>>();
        this.nodes = new TreeMap<K, PoNode<T>>();
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
            node = createNode();
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

    public synchronized <N extends PoNode<? extends T>> N getRoot() {
        if (roots.isEmpty())
            throw new IllegalStateException("No root node in the tree.");
        if (roots.size() > 1)
            throw new IllegalStateException("Too many root nodes in the tree!");
        return (N) roots.iterator().next();
    }

    public <N extends PoNode<? extends T>> N getNode(K key) {
        return (N) nodes.get(key);
    }

    protected synchronized PoNode<T> getOrCreateVirtualNode(K key) {
        if (key == null)
            return null;

        PoNode<T> node = nodes.get(key);
        if (node == null) {
            node = createNode();
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

    public Class<? extends PoNode<? extends T>> getNodeClass() {
        return nodeClass;
    }

    public void setNodeClass(Class<? extends PoNode<? extends T>> nodeClass) {
        this.nodeClass = nodeClass;
    }

    protected PoNode<T> createNode() {
        PoNode<T> node;
        try {
            node = (PoNode<T>) nodeClass.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return node;
    }

    public IFormatter<PoNode<?>> getFormatter() {
        return formatter;
    }

    public void setFormatter(IFormatter<PoNode<?>> formatter) {
        this.formatter = formatter;
    }

    public String dump() {
        PrettyPrintStream buf = new PrettyPrintStream();
        dump(buf);
        return buf.toString();
    }

    public void dump(IPrintOut out) {
        for (PoNode<T> root : roots) {
            out.println("Root " + root.getKey());
            dump(out, root, formatter);
        }
    }

    public static void dump(IPrintOut out, PoNode<?> node, IFormatter<PoNode<?>> formatter) {
        _dump(out, node, formatter, "");
    }

    static void _dump(IPrintOut out, PoNode<?> node, IFormatter<PoNode<?>> formatter, String prefix) {
        List<? extends PoNode<?>> children = node.getChildren();
        for (int i = 0; i < children.size(); i++) {
            PoNode<?> child = children.get(i);
            boolean end = i == children.size() - 1;

            String str = formatter.format(child);
            out.println(prefix + (end ? " `- " : " |- ") + str);

            _dump(out, child, formatter, prefix + (end ? "    " : " |  "));
        }
    }

}
