package com.bee32.plover.orm.ext.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.ext.color.UIEntitySpec;

/**
 * A base entity type for self organized tree model.
 *
 * @param K
 *            The key/identity type.
 * @param $
 *            The same type as declared.
 *
 */
@MappedSuperclass
public abstract class TreeEntity<K extends Serializable, $ extends TreeEntity<K, $>>
        extends UIEntitySpec<K> {

    private static final long serialVersionUID = 1L;

    $ parent;
    List<$> children = new ArrayList<$>();

    @Transient
    public boolean isRoot() {
        return parent != null;
    }

    @ManyToOne
    public $ getParent() {
        return parent;
    }

    public void setParent($ parent) {
        this.parent = parent;
    }

    /**
     * WARNING: Cascaded by all, but never DELETE_ORPHAN.
     */
    @OneToMany(mappedBy = "parent")
    @Cascade(CascadeType.ALL)
    public List<$> getChildren() {
        return children;
    }

    public void setChildren(List<$> children) {
        if (children == null)
            throw new NullPointerException("children");
        this.children = children;
    }

    /**
     * TODO conformadate to PrincipalDiag#checkDeadLoop.
     */
    public void checkCircularLoop() {
    }

}
