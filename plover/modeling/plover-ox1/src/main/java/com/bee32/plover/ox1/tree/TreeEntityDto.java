package com.bee32.plover.ox1.tree;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.free.ICharOut;
import javax.free.ParseException;
import javax.free.Stdio;
import javax.free.TypeConvertException;
import javax.persistence.Transient;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;

public abstract class TreeEntityDto<E extends TreeEntity<K, E>, K extends Serializable, T extends TreeEntityDto<E, K, T>>
        extends UIEntityDto<E, K> {

    private static final long serialVersionUID = 1L;

    public static final int PARENT = 0x00010000;
    public static final int CHILDREN = 0x00020000;

    T parent;
    List<T> children;

    public TreeEntityDto() {
        super();
    }

    public TreeEntityDto(int selection) {
        super(selection);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void __marshal(E source) {
        super.__marshal(source);

        if (selection.contains(PARENT))
            parent = (T) mref(getClass(), selection.bits, source.getParent());

        if (selection.contains(CHILDREN))
            children = mrefList(getClass(), selection.bits, source.getChildren());
    }

    @Override
    protected void __unmarshalTo(E target) {
        super.__unmarshalTo(target);

        if (selection.contains(PARENT))
            merge(target, "parent", parent);

        if (selection.contains(CHILDREN))
            mergeList(target, "children", children);
    }

    @Override
    protected void __parse(TextMap map)
            throws ParseException, TypeConvertException {
        super.__parse(map);

        K parentId = parseId(map.getString("parent"));

        if (selection.contains(PARENT))
            parent = createAnother().ref(parentId);

        if (selection.contains(CHILDREN)) {
            children = new ArrayList<T>();
            for (TextMap childMap : map.shift("child")) {
                String _childId = childMap.getString("id");
                K childId = parseId(_childId);
                T child = createAnother().ref(childId);
                children.add(child);
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected final T self() {
        return (T) this;
    }

    protected T createAnother() {
        try {
            return (T) getClass().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate another DTO: " + e.getMessage(), e);
        }
    }

    public T getParent() {
        return parent;
    }

    public void setParent(T parent) {
        this.parent = parent;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        if (children == null)
            throw new NullPointerException("children");
        this.children = children;
    }

    public int indexOf(T child) {
        return children.indexOf(child);
    }

    public int getIndex() {
        if (parent == null)
            return 0;
        T self = self();
        return parent.indexOf(self);
    }

    public int size() {
        return children.size();
    }

    public int getDepth() {
        int depth = 0;
        T node = self();
        while (node != null) {
            node = node.getParent();
            depth++;
        }
        return depth;
    }

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isFirst() {
        return getIndex() == 0;
    }

    public boolean isLast() {
        return getIndex() == parent.size() - 1;
    }

    @Transient
    public List<T> getChain() {
        List<T> chain = new ArrayList<T>();
        T node = self();
        while (node != null) {
            chain.add(node);
            node = node.parent;
        }
        Collections.reverse(chain);
        return chain;
    }

    public String getGraphPrefix() {
        if (parent == null)
            return "";

        StringBuilder buf = new StringBuilder();

        if (!isLast())
            buf.append(" -| "); // _|-_
        else
            buf.append(" -` "); // _`-_

        T node = parent;
        while (node != null) {
            if (!node.isLast())
                buf.append("  | "); // _|__
            else
                buf.append("    "); // ____
            node = node.getParent();
        }

        buf.reverse();
        return buf.toString();
    }

    public String getNodeLabel() {
        // return naturalId().toString();
        String str = getName();
        if (str == null)
            str = "(noname)";

        String label = getLabel();
        if (label != null)
            str += " 【" + label + "】";

        return str;
    }

    void dump(ICharOut out)
            throws IOException {
        out.write(getGraphPrefix());
        out.write(getNodeLabel());
        out.write('\n');

        for (T child : children)
            child.dump(out);
    }

    public void dump()
            throws IOException {
        dump(Stdio.cout);
    }

}
