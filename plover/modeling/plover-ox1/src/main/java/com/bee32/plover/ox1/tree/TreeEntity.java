package com.bee32.plover.ox1.tree;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.free.ICharOut;
import javax.free.IllegalUsageException;
import javax.free.Stdio;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.ox1.color.UIEntitySpec;

/**
 * A base entity type for self organized tree model.
 *
 * @param K
 *            The key/identity type.
 * @param T
 *            The same type as declared.
 *
 */
@MappedSuperclass
public abstract class TreeEntity<K extends Serializable, self_t extends TreeEntity<K, self_t>>
        extends UIEntitySpec<K> {

    private static final long serialVersionUID = 1L;

    self_t parent;
    List<self_t> children = new ArrayList<self_t>();

    public TreeEntity() {
        this(null);
    }

    public TreeEntity(self_t parent) {
        this.parent = parent;
        if (parent != null)
            parent.addChild(self());
    }

    @SuppressWarnings("unchecked")
    protected final self_t self() {
        return (self_t) this;
    }

    @ManyToOne
    public self_t getParent() {
        return parent;
    }

    public void setParent(self_t parent) {
        if (parent != null)
            checkNode(false, parent);
        this.parent = parent;
    }

    /**
     * WARNING: Cascaded by all, but never DELETE_ORPHAN.
     */
    @OneToMany(mappedBy = "parent")
    @Cascade(CascadeType.ALL)
    public List<self_t> getChildren() {
        return children;
    }

    public void setChildren(List<self_t> children) {
        if (children == null)
            throw new NullPointerException("children");
        this.children = children;
    }

    @Transient
    protected boolean isUniqueChildren() {
        return false;
    }

    public boolean addChild(self_t child) {
        if (child == null)
            throw new NullPointerException("child");

        checkNode(true, child);

        if (isUniqueChildren())
            if (children.contains(child))
                return false;

        children.add(child);
        child.setParent(self());
        return true;
    }

    public boolean removeChild(self_t child) {
        if (child == null)
            throw new NullPointerException("child");

        checkNode(true, child);

        if (!children.remove(child))
            return false;

        child.setParent(null);
        return true;
    }

    @Transient
    public boolean isRoot() {
        return parent != null;
    }

    int indexOf(self_t child) {
        return children.indexOf(child);
    }

    @Transient
    public int getIndex() {
        if (parent == null)
            return 0;
        self_t self = self();
        return parent.indexOf(self);
    }

    public int size() {
        return children.size();
    }

    @Redundant
    @Column(nullable = false)
    public int getDepth() {
        int safeDepth = getSafeDepth();
        int depth = 0;
        self_t node = self();
        while (node != null) {
            node = node.getParent();
            depth++;
            if (depth >= safeDepth)
                throw new IllegalUsageException(
                        "Exceeds the max depth of a tree, maybe there's dead loop? Last node = " + node);
        }
        return depth;
    }

    void setDepth(int depth) {
    }

    /**
     * In most cases, 1000 is really enough.
     */
    @Transient
    protected int getSafeDepth() {
        return 1000;
    }

    @Transient
    public boolean isFirst() {
        if (parent == null)
            return true;
        else
            return getIndex() == 0;
    }

    @Transient
    public boolean isLast() {
        if (parent == null)
            return true;
        else
            return getIndex() == parent.size() - 1;
    }

    @Transient
    public List<self_t> getChain() {
        List<self_t> chain = new ArrayList<self_t>();
        self_t node = self();
        while (node != null) {
            chain.add(node);
            node = node.parent;
        }
        Collections.reverse(chain);
        return chain;
    }

    @Transient
    public String getGraphPrefix() {
        if (parent == null)
            return "";

        StringBuilder buf = new StringBuilder();

        if (!isLast())
            buf.append(" -| "); // _|-_
        else
            buf.append(" -` "); // _`-_

        self_t node = parent;
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

    @Transient
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

    /**
     * Check if the given node may be added to the tree.
     *
     * @param isChild
     *            <code>true</code> for parent node, <code>false</code> for children.
     * @param node
     *            Non-<code>null</code> node to be checked.
     * @throws IllegalArgumentException
     *             If the node type is illegal.
     */
    protected void checkNode(boolean isChild, self_t node) {
        Class<?> selfType = getClass();
        if (!selfType.isInstance(node))
            throw new IllegalArgumentException("Inconsistent node type: tree=" + selfType + ", node=" + node.getClass());
    }

    /**
     * TODO conformadate to PrincipalDiag#checkDeadLoop.
     *
     * @param order
     *            Max number of nodes in the graph, this enables fast check.
     */
    public boolean checkLoopFast(int order) {
        if (order < 1)
            throw new IllegalArgumentException("Order should be positive integer: " + order);
        self_t node = self();
        for (int i = 0; i < order; i++) {

            self_t _node = node.getParent();
            node = _node;

            if (node == null)
                return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    protected static <S, T> List<T> cast(List<S> sv) {
        List<T> tv = new ArrayList<T>();
        for (S s : sv) {
            T t = (T) s;
            tv.add(t);
        }
        return tv;
    }

    void dump(ICharOut out)
            throws IOException {
        out.write(getGraphPrefix());
        out.write(getNodeLabel());
        out.write('\n');

        for (self_t child : children)
            child.dump(out);
    }

    public void dump()
            throws IOException {
        dump(Stdio.cout);
    }

}
