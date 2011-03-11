package com.bee32.plover.orm.entity;

public class Cat
        extends Entity<Long> {

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

}
