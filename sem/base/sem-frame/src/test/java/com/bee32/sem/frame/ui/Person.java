package com.bee32.sem.frame.ui;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.orm.entity.EntitySpec;

public class Person
        extends EntitySpec<String> {

    private static final long serialVersionUID = 1L;

    String name;
    int age;
    boolean girl;

    public Person() {
    }

    public Person(String name, int age, boolean girl) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String getId() {
        return getName();
    }

    @Override
    protected void setId(String id) {
        setName(id);
    }

    @NLength(min = 3, max = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Min(5)
    @Max(100)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isGirl() {
        return girl;
    }

    public void setGirl(boolean girl) {
        this.girl = girl;
    }

}
