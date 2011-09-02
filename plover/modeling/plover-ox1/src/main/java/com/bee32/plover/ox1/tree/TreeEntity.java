package com.bee32.plover.ox1.tree;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.free.ICharOut;
import javax.free.Stdio;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.criteria.hibernate.Alias;
import com.bee32.plover.criteria.hibernate.And;
import com.bee32.plover.criteria.hibernate.CriteriaComposite;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.IsNull;
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
public abstract class TreeEntity<K extends Serializable, T extends TreeEntity<K, T>>
        extends UIEntitySpec<K> {

    private static final long serialVersionUID = 1L;

    T parent;
    List<T> children = new ArrayList<T>();

    public TreeEntity() {
        super();
    }

    public TreeEntity(String name) {
        super(name);
    }

    public TreeEntity(T parent, String name) {
        super(name);
        if (parent != null)
            parent.addChild(self());
    }

    @SuppressWarnings("unchecked")
    protected final T self() {
        return (T) this;
    }

    @ManyToOne
    public T getParent() {
        return parent;
    }

    public void setParent(T parent) {
        if (parent != null)
            checkNode(false, parent);
        this.parent = parent;
    }

    /**
     * WARNING: Cascaded by all, but never DELETE_ORPHAN.
     */
    @OneToMany(mappedBy = "parent")
    @Cascade(CascadeType.ALL)
    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        if (children == null)
            throw new NullPointerException("children");
        this.children = children;
    }

    public void addChild(T child) {
        if (child == null)
            throw new NullPointerException("child");

        checkNode(true, child);

        child.setParent(self());
        children.add(child);
    }

    public boolean removeChild(T child) {
        if (child == null)
            throw new NullPointerException("child");

        checkNode(true, child);

        child.setParent(null);
        return children.remove(child);
    }

    @Transient
    public boolean isRoot() {
        return parent != null;
    }

    int indexOf(T child) {
        return children.indexOf(child);
    }

    @Transient
    public int getIndex() {
        if (parent == null)
            return 0;
        T self = self();
        return parent.indexOf(self);
    }

    public int size() {
        return children.size();
    }

    @Redundant
    @Column(nullable = false)
    public int getDepth() {
        int depth = 0;
        T node = self();
        while (node != null) {
            node = node.getParent();
            depth++;
        }
        return depth;
    }

    void setDepth(int depth) {
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
    protected void checkNode(boolean isChild, T node) {
        Class<?> selfType = getClass();
        Class<?> nodeType = node.getClass();
        if (!selfType.equals(nodeType))
            throw new IllegalArgumentException("Inconsistent node type: tree=" + selfType + ", node=" + nodeType);
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
        T node = self();
        for (int i = 0; i < order; i++) {

            T _node = node.getParent();
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

        for (T child : children)
            child.dump(out);
    }

    public void dump()
            throws IOException {
        dump(Stdio.cout);
    }

    protected ICriteriaElement localSelector(String prefix) {
        return super.selector(prefix);
    }

    protected ICriteriaElement selector(String prefix) {
        ICriteriaElement localSelector = localSelector(prefix);

        T parent = getParent();
        if (parent == null)
            return new And(//
                    new IsNull(prefix + "parent"), //
                    localSelector);

        K parentId = getParent().getId();
        if (parentId == null)
            return null; // parent hasn't been saved.

        return new CriteriaComposite(//
                new Alias(prefix + "parent", "parent"), //
                new Equals(prefix + "parent.id", parentId), //
                localSelector);
    }

}
