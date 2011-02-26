package com.bee32.plover.orm.entity;

public class Cat
        extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String color;

    public Cat() {
    }

    public Cat(String name, String color) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

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

    @Override
    public String toString() {
        return String.format(getClass().getSimpleName() + " %s (%d) is %s", name, id, color);
    }

}
