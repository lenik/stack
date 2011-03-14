package com.bee32.plover.orm.entity;

import javax.free.Nullables;
import javax.persistence.Entity;

@Entity
public class Cat
        extends EntityBean<Long> {

    private static final long serialVersionUID = 1L;

    private String color;

    public Cat() {
    }

    public Cat(String name, String color) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
