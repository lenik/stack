package com.bee32.plover.orm.entity;

import java.util.Collection;

import javax.free.Nullables;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Cat
        extends EntityBean<Long> {

    private static final long serialVersionUID = 1L;

    private String color;

    private Cat parent;
    private Collection<Cat> children;

    public Cat() {
    }

    public Cat(String name, String color) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
        this.color = color;
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
    public Cat getParent() {
        return parent;
    }

    public void setParent(Cat parent) {
        this.parent = parent;
    }

    @OneToMany(mappedBy = "parent")
    public Collection<Cat> getChildren() {
        return children;
    }

    public void setChildren(Collection<Cat> children) {
        this.children = children;
    }

    @Override
    protected boolean equalsEntity(EntityBean<Long> otherEntity) {
        Cat o = (Cat) otherEntity;

        if (!Nullables.equals(color, o.color))
            return false;

        return true;
    }

    @Override
    protected int hashCodeEntity() {
        return color == null ? 0 : color.hashCode();
    }

}
