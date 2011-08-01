package com.bee32.plover.orm.feaCat;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NaturalId;

import com.bee32.plover.orm.entity.EntityBase;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "cat_seq", allocationSize = 1)
public class Cat
        extends SuperEntity<Long> {

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

    @NaturalId
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
    public Cat getParent() {
        return parent;
    }

    public void setParent(Cat parent) {
        this.parent = cast(parent);
    }

    @ManyToOne
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
                    // Using LinkedHashSet to keep order for test.
                    children = new LinkedHashSet<Cat>();
                }
            }
        }
        return children;
    }

    public void setChildren(Set<Cat> children) {
        this.children = children;
    }

    @Override
    protected Boolean naturalEquals(EntityBase<Long> other) {
        Cat o = (Cat) other;

        if (name == null || o.name == null)
            return false;

        return name.equals(o.name);
    }

    @Override
    protected Integer naturalHashCode() {
        return name == null ? 0 : name.hashCode();
    }

}
