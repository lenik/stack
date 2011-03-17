package com.bee32.plover.orm.feaCat;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.free.Nullables;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.entity.EntityBean;

@Entity
public class Cat
        extends EntityBean<Long> {

    private static final long serialVersionUID = 1L;

    String color;

    Cat leader;
    Cat parent;
    Set<Cat> children;

    public Cat() {
    }

    public Cat(String name, String color) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
        this.color = color;
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @Override
    public void setVersion(int version) {
        super.setVersion(version);
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @ManyToOne
    // (fetch = FetchType.LAZY)
    public Cat getParent() {
        return parent;
    }

    public void setParent(Cat parent) {
        this.parent = parent;
    }

    @ManyToOne
    // (fetch = FetchType.LAZY)
    public Cat getLeader() {
        return leader;
    }

    public void setLeader(Cat leader) {
        this.leader = leader;
    }

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    public Set<Cat> getChildren() {
        if (children == null) {
            synchronized (this) {
                if (children == null) {
                    children = new HashSet<Cat>();
                }
            }
        }
        return children;
    }

    public void setChildren(Collection<? extends Cat> children) {
        this.children = new HashSet<Cat>(children);
    }

    @Override
    protected boolean equalsEntity(EntityBean<Long> otherEntity) {
        Cat o = (Cat) otherEntity;

        if (!Nullables.equals(color, o.color))
            return false;

        if (!Nullables.equals(parent, o.parent))
            return false;

        if (!Nullables.equals(leader, o.leader))
            return false;

        return true;
    }

    @Override
    protected int hashCodeEntity() {
        final int prime = 31;
        int result = 0;
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        result = prime * result + ((leader == null) ? 0 : leader.hashCode());
        result = prime * result + ((parent == null) ? 0 : parent.hashCode());
        return result;
    }

}
